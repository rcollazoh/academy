package cu.academy.student.course;

import cu.academy.config.classes.ConfigClassEntity;
import cu.academy.config.classes.ConfigClassService;
import cu.academy.config.course.ConfigCourseEntity;
import cu.academy.config.course.ConfigCourseService;
import cu.academy.config.exam.ConfigExamEntity;
import cu.academy.config.exam.ConfigExamRepository;
import cu.academy.config.module.ConfigModuleEntity;
import cu.academy.config.module.ConfigModuleService;
import cu.academy.config.parameter.ConfigParameterService;
import cu.academy.email.EmailService;
import cu.academy.person.PersonRepository;
import cu.academy.shared.configs.text_messages.Translator;
import cu.academy.shared.enum_types.EnumCourseStatus;
import cu.academy.shared.enum_types.EnumModuleStatus;
import cu.academy.shared.enum_types.EnumPaymentMethod;
import cu.academy.shared.exceptions.ArgumentException;
import cu.academy.shared.utils.TranslatorCode;
import cu.academy.student.classes.StudentClassEntity;
import cu.academy.student.classes.StudentClassService;
import cu.academy.student.exam.StudentExamEntity;
import cu.academy.student.exam.StudentExamRepository;
import cu.academy.student.module.StudentModuleEntity;
import cu.academy.student.module.StudentModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class StudentCourseService {
    private final StudentCourseRepository studentCourserepository;
    private final PersonRepository personRepository;
    private final StudentModuleRepository studentModuleRepository;
    private final StudentClassService studentClassService;
    private final StudentExamRepository studentExamRepository;
    private final ConfigCourseService configCourseService;
    private final ConfigModuleService configModuleService;
    private final ConfigClassService configClassService;
    private final EmailService emailService;
    private final ConfigExamRepository configExamRepository;
    private final ConfigParameterService parameterService;



//    private final ModelMapper modelMapper;
//    private static final Type listType = new TypeToken<List<NomAplicacionRespRedDto>>() {
//    }.getType();

    @Autowired
    public StudentCourseService(StudentCourseRepository repository, PersonRepository personRepository, ConfigCourseService configCourseService, StudentModuleRepository studentModuleService, StudentClassService studentClassService, StudentExamRepository studentExamService, ConfigModuleService configModuleService, ConfigClassService configClassService, EmailService emailService, ConfigExamRepository configExamRepository, ConfigParameterService parameterService) {
        this.studentCourserepository = repository;
        this.personRepository = personRepository;
        this.configCourseService = configCourseService;
        this.studentModuleRepository = studentModuleService;
        this.studentClassService = studentClassService;
        this.studentExamRepository = studentExamService;
        this.configModuleService = configModuleService;
        this.configClassService = configClassService;
        this.emailService = emailService;
        this.configExamRepository = configExamRepository;
        this.parameterService = parameterService;
    }

    public StudentCourseEntity getById(Long id) throws ArgumentException {
        return (studentCourserepository.findById(id))
                .orElseThrow(() -> new ArgumentException(Translator.toLocale(TranslatorCode.NO_TIPO_APLICACION)));
    }

    public List<StudentCourseEntity> getAllSort() {
        return studentCourserepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    public List<StudentCourseEntity> getFindByPersonId(long personId) {
        return studentCourserepository.findByPersonId(personId);
    }

    public StudentCourseEntity getStudentCourseByAreaAndPractice(long personId, long areaId, long practiceId) {
        // Define the valid statuses to check for existing enrollments
        List<EnumCourseStatus> validStatuses = List.of(EnumCourseStatus.PENDING, EnumCourseStatus.ACTIVATED);

        // Look for any existing student course with matching personId and valid status
        List<StudentCourseEntity> existing = studentCourserepository.findByPersonIdAndStatusIn(personId, validStatuses);
        StudentCourseEntity result;

        if (!existing.isEmpty()) {
            // The student already has a course in progress or pending — return it
            result = existing.get(0);
        } else {
            // No active or pending course found — look up the available course for the given area and practice
            ConfigCourseEntity course = configCourseService.getCourseByAreaAndPractice(areaId, practiceId);
            if (course == null) {
                throw new ArgumentException("No course available for the given area and practice");
            }

            // Create a temporary course enrollment (not persisted) to suggest to the student
            result = new StudentCourseEntity();
            result.setPersonId(personId);
            result.setCourse(course);
            result.setStatus(EnumCourseStatus.NEW); // Simulated status, it is a flag
            result.setStartDate(LocalDate.now());
        }
        return result;
    }

    @Transactional
    public void delete(Long id) throws ArgumentException {
        if (studentCourserepository.existsById(id)) {
            studentCourserepository.deleteById(id);
        } else {
            throw new ArgumentException(Translator.toLocale(TranslatorCode.NO_TIPO_APLICACION));
        }
    }

    @Transactional
    public StudentCourseEntity insert(StudentCourseEntity entity) {
        return studentCourserepository.save(entity);
    }

    @Transactional
    public StudentCourseEntity update(Long id, StudentCourseEntity entity) throws ArgumentException {
        if (!studentCourserepository.existsById(id)) {
            throw new ArgumentException(Translator.toLocale(TranslatorCode.NO_TIPO_APLICACION));
        }
        return studentCourserepository.save(entity);
    }

    @Transactional
    public void applyStudentCourse(Long personId, Long courseId, EnumPaymentMethod paymentMethod, MultipartFile paymentPhoto) {
        StudentCourseEntity studentEntity = new StudentCourseEntity();

        studentEntity.setPersonId(personId);
        studentEntity.setCourse(configCourseService.getById(courseId));
        studentEntity.setStatus(EnumCourseStatus.PENDING);
        studentEntity.setPaymentMethod(paymentMethod);
        insert(studentEntity);

        emailService.sendMessage(
                personRepository.getReferenceById(personId).getEmail(),
                "Confirmación de aplicación a curso",
                "Estimado/a estudiante,\n\nUsted ha aplicado exitosamente a un nuevo curso en Prod Academy.\n\nPor favor, espere un próximo correo de confirmación una vez que el profesor haya aprobado su solicitud.\n\nGracias por confiar en nosotros.\n\nAtentamente,\nEl equipo de Prod Academy",
                null
        );

        // crear notificacion  y correo para Acedemy.
        emailService.sendMessage(
                parameterService.getBy("USUARIO_CORREO_EMISOR").getValue(),
                "Confirmación de aplicación a curso",
                "Aplico un estudiante",
                null
        );
//       insertFotoTransferencia(depositoInserted, fotoTransferencia);

    }

    @Transactional
    public void activeStudentCourse(Long personId, Long courseId) {
        StudentCourseEntity studentEntity = getById(courseId);
        studentEntity.setStatus(EnumCourseStatus.ACTIVATED);
        studentEntity.setStartDate(LocalDate.now());
        StudentCourseEntity studentCourseinsert = insert(studentEntity);


        List<ConfigModuleEntity> moduleEntities = configModuleService.getModuleByCourse(courseId);
        for (ConfigModuleEntity moduleEntity : moduleEntities) {
            StudentModuleEntity entityModuleTemp = new StudentModuleEntity();
            entityModuleTemp.setStudentCourse(studentCourseinsert);
            entityModuleTemp.setModule(moduleEntity);
            entityModuleTemp.setStatus(EnumModuleStatus.NEW);
            StudentModuleEntity studentModuleInsert = studentModuleRepository.save(entityModuleTemp);

            // Aqui van las claseses del modulo
            List<ConfigClassEntity> classByModule = configClassService.getClassByModule(moduleEntity.getId());
            for (ConfigClassEntity configClassEntity : classByModule) {
                StudentClassEntity studentClassEntity = new StudentClassEntity();
                studentClassEntity.setStudentModule(studentModuleInsert);
                studentClassEntity.setConfigClass(configClassEntity);
                studentClassService.insert(studentClassEntity);
            }

            //Aqui va el examen
            Optional<ConfigExamEntity> byConfigModuleId = configExamRepository.findByConfigModuleId(moduleEntity.getId());
            if (byConfigModuleId.isPresent()) {
                StudentExamEntity studentExamEntity = new StudentExamEntity();
                studentExamEntity.setStudentModule(studentModuleInsert);
                studentExamEntity.setConfigExam(byConfigModuleId.get());
                studentExamRepository.save(studentExamEntity);
            }
        }

        emailService.sendMessage(
                personRepository.getReferenceById(personId).getEmail(),
                "Confirmación de aprobación de curso",
                "Estimado/a estudiante,\n\nNos complace informarle que su solicitud para el curso ha sido aprobada por el profesor.\n\nYa puede acceder al contenido del curso y comenzar su formación en Prod Academy.\n\nSi tiene alguna duda o necesita asistencia, no dude en contactarnos.\n\n¡Le deseamos mucho éxito en su aprendizaje!\n\nAtentamente,\nEl equipo de Prod Academy",
                null
        );
    }

    @Transactional
    public void rejectCourse(long id, long personId) {
        studentCourserepository.updateStatusById(id, EnumCourseStatus.REJECTED);

        emailService.sendMessage(
                personRepository.getReferenceById(personId).getEmail(),
                "Su curso fue rechazado",
                "Estimado/a estudiante,\n\nPor los problemas presentados, la solicitud de curso fue rechazada.\n\nSi tiene alguna duda o necesita asistencia, no dude en contactarnos.\n\nAtentamente,\nEl equipo de Prod Academy",
                null
        );
    }

}