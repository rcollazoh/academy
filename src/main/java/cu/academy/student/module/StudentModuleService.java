package cu.academy.student.module;

import cu.academy.config.parameter.ConfigParameterService;
import cu.academy.config.reference.ConfigReferenceEntity;
import cu.academy.config.reference.ConfigReferenceService;
import cu.academy.config.reference.dto.ConfigReferenceDto;
import cu.academy.email.EmailService;
import cu.academy.person.PersonRepository;
import cu.academy.shared.configs.text_messages.Translator;
import cu.academy.shared.enum_types.EnumCourseStatus;
import cu.academy.shared.enum_types.EnumExamStatus;
import cu.academy.shared.enum_types.EnumModuleStatus;
import cu.academy.shared.exceptions.ArgumentException;
import cu.academy.shared.utils.TranslatorCode;
import cu.academy.student.classes.StudentClassEntity;
import cu.academy.student.classes.StudentClassService;
import cu.academy.student.classes.dto.StudentClassDto;
import cu.academy.student.course.StudentCourseEntity;
import cu.academy.student.course.StudentCourseService;
import cu.academy.student.exam.StudentExamEntity;
import cu.academy.student.exam.StudentExamRepository;
import cu.academy.student.exam.dto.StudentExamDto;
import cu.academy.student.module.dto.StudentModuleDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentModuleService {
    private final StudentModuleRepository repository;
    private final StudentExamRepository examRepository;
    private final StudentClassService studentClassService;
    private final EmailService emailService;
    private final PersonRepository personRepository;
    private final ConfigParameterService parameterService;
    private final StudentCourseService studentCourseService;
    private final ConfigReferenceService configReferenceService ;
    private static final Logger log = LoggerFactory.getLogger(StudentModuleService.class);

    @Autowired
    public StudentModuleService(StudentModuleRepository repository, StudentExamRepository examRepository, StudentClassService studentClassService, EmailService emailService, PersonRepository personRepository, ConfigParameterService parameterService, StudentCourseService studentCourseService, ConfigReferenceService configReferenceService) {
        this.repository = repository;
        this.examRepository = examRepository;
        this.studentClassService = studentClassService;
        this.emailService = emailService;
        this.personRepository = personRepository;
        this.parameterService = parameterService;
        this.studentCourseService = studentCourseService;
        this.configReferenceService = configReferenceService;
    }

    @Transactional
    public StudentModuleEntity insert(StudentModuleEntity entity) {
        return repository.save(entity);
    }

    @Transactional
    public void updateStatus(Long id, EnumModuleStatus status) {
        repository.updateStatusById(id, status);
    }

    public StudentModuleEntity getById(Long id) throws ArgumentException {
        return (repository.findById(id))
                .orElseThrow(() -> new ArgumentException(Translator.toLocale(TranslatorCode.NO_EXISTE_ELEMENT)));
    }

    public List<StudentModuleEntity> getAllSort() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    public List<StudentModuleDto> getAllModuleByCourseId(Long courseId) {
        List<StudentModuleDto> result = new ArrayList<>();
        List<StudentModuleEntity> modules = repository.findFullModulesByStudentCourseId(courseId);
        for (StudentModuleEntity module : modules) {
            List<StudentClassEntity> classes = studentClassService.getAllByModuleId(module.getId());
            Optional<StudentExamEntity> exam = examRepository.findByConfigModuleId(module.getId());
            List<ConfigReferenceEntity> references = new ArrayList<>();
            if (Translator.toLocale(TranslatorCode.NAME_MODULE_REFERENCE).equals(module.getModule().getName()))
                references = configReferenceService.getAllReferenceByModuleId(module.getModule().getId());

            // 4. Mapear clases y examen a sus respectivos DTOs
            List<StudentClassDto> classDtos = new ArrayList<>();
            for (StudentClassEntity classEntity : classes) {
                classDtos.add(new StudentClassDto(classEntity.getId(), classEntity.getCurrentImageId(),
                        classEntity.getViewed(), classEntity.getConfigClass().getId(),
                        classEntity.getConfigClass().getTitle(), classEntity.getConfigClass().getType()));
            }

            StudentExamDto examDto = null;
            if (exam.isPresent()) {
                examDto = new StudentExamDto(exam.get().getId(), exam.get().getStatus(),
                        exam.get().getConfigExam().getId(), exam.get().getConfigExam().getTitle(),
                        exam.get().getConfigExam().getDurationMinutes(),
                        exam.get().getConfigExam().getMinQuestions(),
                        exam.get().getConfigExam().getQuestions());
            }

            List<ConfigReferenceDto> referencesDtos = new ArrayList<>();
            for (ConfigReferenceEntity entity : references) {
                referencesDtos.add(new ConfigReferenceDto(entity.getId(), entity.getTitle(),
                        entity.getLink(), entity.getOrderNum()));
            }

            // 5. create dto module
            StudentModuleDto dto = new StudentModuleDto(
                    module.getId(),
                    module.getModule().getId(),
                    module.getStatus(),
                    module.getModule().getName(),
                    module.getModule().getOrderNum(),
                    classDtos,
                    examDto,
                    referencesDtos
            );
            result.add(dto);
        }
        return result;
    }

    @Transactional
    public EnumExamStatus updateModuleAndEvaluateCourse(StudentModuleEntity entityModule, EnumExamStatus statusModule) {
//        updateStatus(entityModule.getId(),EnumModuleStatus.valueOf(status.name()));
        entityModule.setStatus(EnumModuleStatus.valueOf(statusModule.name()));
        entityModule.setFechaExam(LocalDate.now());
        entityModule.setIntentos(entityModule.getIntentos() + 1);
        //update
        insert(entityModule);
//
        List<EnumModuleStatus> statuses = repository.findStatusesByCourseId(entityModule.getStudentCourse().getId().longValue());

        boolean hasNotApproved = statuses.stream()
                .anyMatch(status -> status == EnumModuleStatus.NOT_APPROVED);

        boolean allApproved = statuses.stream()
                .allMatch(status -> status == EnumModuleStatus.APPROVED);

        boolean allFinalized = statuses.stream()
                .allMatch(status -> status == EnumModuleStatus.APPROVED || status == EnumModuleStatus.NOT_APPROVED);

        if (hasNotApproved) {
            try {
            emailService.sendEmail(
                    parameterService.getBy("USUARIO_CORREO_EMISOR").getValue(),
                    TranslatorCode.COURSE_DISAPPROVE_TOPIC,
                    TranslatorCode.COURSE_DISAPPROVE_BODY,
                    null,null
            );
                log.info("Correo enviado correctamente de desaprobado.");
            } catch (Exception e) {
                log.warn("No se pudo enviar el correo de desaprobado: " + e.getMessage());
                // Opcional: registrar en BD o sistema de alertas
            }
        }

        if (allApproved) {
            // Aprobar el curso y notificar al profesor
            StudentCourseEntity byIdCourse = entityModule.getStudentCourse();
            byIdCourse.setStatus(EnumCourseStatus.APPROVED);
            studentCourseService.update(entityModule.getStudentCourse().getId().longValue(), byIdCourse);
            try {
            emailService.sendEmail(
                    personRepository.getReferenceById(byIdCourse.getPersonId()).getEmail(),
                    TranslatorCode.COURSE_APPROVE_TOPIC,
                    TranslatorCode.COURSE_APPROVE_BODY,
                    null,null
            );
            emailService.sendEmail(
                    parameterService.getBy("USUARIO_CORREO_EMISOR").getValue(),
                    TranslatorCode.COURSE_FINALIZED_TOPIC,
                    TranslatorCode.COURSE_FINALIZED_BODY,
                    null,null
            );
                log.info("Correo enviado correctamente de finalizar.");
            } catch (Exception e) {
                log.warn("No se pudo enviar el correo para finalizar: " + e.getMessage());
                // Opcional: registrar en BD o sistema de alertas
            }
        }
        return allFinalized ? (!allApproved ? EnumExamStatus.NOT_APPROVED : EnumExamStatus.APPROVED) : EnumExamStatus.ACTIVE;
    }
}