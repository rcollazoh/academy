package cu.academy.student.course;

import cu.academy.config.classes.ConfigClassEntity;
import cu.academy.config.classes.ConfigClassService;
import cu.academy.config.course.ConfigCourseService;
import cu.academy.config.exam.ConfigExamEntity;
import cu.academy.config.exam.ConfigExamRepository;
import cu.academy.config.module.ConfigModuleEntity;
import cu.academy.config.module.ConfigModuleService;
import cu.academy.config.parameter.ConfigParameterService;
import cu.academy.email.EmailService;
import cu.academy.images.FilesStorageService;
import cu.academy.person.PersonRepository;
import cu.academy.shared.configs.text_messages.Translator;
import cu.academy.shared.dto.PagingResponseList;
import cu.academy.shared.enum_types.EnumCourseStatus;
import cu.academy.shared.enum_types.EnumImagenType;
import cu.academy.shared.enum_types.EnumModuleStatus;
import cu.academy.shared.enum_types.EnumPaymentMethod;
import cu.academy.shared.exceptions.ArgumentException;
import cu.academy.shared.utils.DateUtils;
import cu.academy.shared.utils.TranslatorCode;
import cu.academy.student.classes.StudentClassEntity;
import cu.academy.student.classes.StudentClassService;
import cu.academy.student.course.mapper.StudentCourseMapper;
import cu.academy.student.exam.StudentExamEntity;
import cu.academy.student.exam.StudentExamRepository;
import cu.academy.student.module.StudentModuleEntity;
import cu.academy.student.module.StudentModuleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
    private final FilesStorageService filesStorageService;


    private static final Logger log = LoggerFactory.getLogger(StudentCourseService.class);

    @Autowired
    public StudentCourseService(StudentCourseRepository repository, PersonRepository personRepository, ConfigCourseService configCourseService, StudentModuleRepository studentModuleService, StudentClassService studentClassService, StudentExamRepository studentExamService, ConfigModuleService configModuleService, ConfigClassService configClassService, EmailService emailService, ConfigExamRepository configExamRepository, ConfigParameterService parameterService, FilesStorageService filesStorageService) {
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
        this.filesStorageService = filesStorageService;
    }

    public StudentCourseEntity getById(Long id) throws ArgumentException {
        return (studentCourserepository.findById(id))
                .orElseThrow(() -> new ArgumentException(Translator.toLocale(TranslatorCode.NO_EXISTE_ELEMENT)));
    }

    public List<StudentCourseEntity> getAllSort() {
        return studentCourserepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    public PagingResponseList<StudentCourseEntity> getAll(Specification<StudentCourseEntity> spec, int page, int size, Sort sort) {
        if (sort != null)
            return get(spec, PageRequest.of(page, size, sort));
        return get(spec, PageRequest.of(page, size));
    }

    /**
     * Retrieve logs using specifications (filter parameters) along with pagination and sorting data.
     *
     * @param spec     parameters used for filtering
     * @param pageable pagination and sorting information
     * @return returns the logs, paginated, sorted, and filtered according to the specified parameters
     */
    public PagingResponseList<StudentCourseEntity> get(Specification<StudentCourseEntity> spec, Pageable pageable) {
        Page<StudentCourseEntity> page = studentCourserepository.findAll(spec, pageable);
        List<StudentCourseEntity> content = page.getContent();
        return new PagingResponseList<>((int) page.getTotalElements(), page.getNumber(),
                page.getNumberOfElements(), (int) pageable.getOffset(), page.getTotalPages(), content);
    }

    public List<StudentCourseEntity> getFindByPersonId(long personId) {
        return studentCourserepository.findByPersonIdOrderByCreatedAtDesc(personId);
    }

    public StudentCourseEntity getStudentCourseActiveByPerson(long personId) {
        // Define the valid statuses to check for existing enrollments
        List<EnumCourseStatus> validStatuses = List.of(EnumCourseStatus.PENDING, EnumCourseStatus.ACTIVATED);

        // Look for any existing student course with matching personId and valid status
        List<StudentCourseEntity> existing = studentCourserepository.findByPersonIdAndStatusIn(personId, validStatuses);
        StudentCourseEntity result = null;

        if (!existing.isEmpty()) {
            // The student already has a course in progress or pending — return it
            result = existing.get(0);
        }else{
            result = new StudentCourseEntity();
            result.setPersonId(personId);
            result.setStatus(EnumCourseStatus.NEW);

        }
        return result;
    }

    @Transactional
    public void delete(Long id) throws ArgumentException {
        if (studentCourserepository.existsById(id)) {
            studentCourserepository.deleteById(id);
        } else {
            throw new ArgumentException(Translator.toLocale(TranslatorCode.NO_EXISTE_ELEMENT));
        }
    }

    @Transactional
    public StudentCourseEntity insert(StudentCourseEntity entity) {
        return studentCourserepository.save(entity);
    }

    @Transactional
    public StudentCourseEntity update(Long id, StudentCourseEntity entity) throws ArgumentException {
        if (!studentCourserepository.existsById(id)) {
            throw new ArgumentException(Translator.toLocale(TranslatorCode.NO_EXISTE_ELEMENT));
        }
        return studentCourserepository.save(entity);
    }

    @Transactional
    public void applyStudentCourse(Long personId, Long configCourseId, EnumPaymentMethod paymentMethod, MultipartFile paymentPhoto) {
        StudentCourseEntity studentEntity = new StudentCourseEntity();
        String extension = filesStorageService.getExtension(paymentPhoto);
        try {
            emailService.sendEmail(
                    parameterService.getBy("USUARIO_CORREO_EMISOR").getValue(),
                    "Confirmación de aplicación a curso",
                    "Aplico un estudiante",
                    paymentPhoto.getBytes(),
                    EnumImagenType.PAYMENT.name().concat(".").concat(extension)
            );
            log.info("Correo enviado correctamente de aplicar.");
        } catch (Exception e) {
            log.warn("No se pudo enviar el correo para aplicar: " + e.getMessage());
            // Opcional: registrar en BD o sistema de alertas
        }
        String receiptUrl = EnumImagenType.PAYMENT.name().
                concat("/").
                concat(DateUtils.getCurrentDateFormat("yyyyMMddHHmmss")).
                concat("-").
                concat(EnumImagenType.PAYMENT.name()).
                concat(personId.toString());
        studentEntity.setPersonId(personId);
        studentEntity.setCourse(configCourseService.getById(configCourseId));
        studentEntity.setStatus(EnumCourseStatus.PENDING);
        studentEntity.setPaymentMethod(paymentMethod);
        studentEntity.setReceiptUrl(receiptUrl.
                concat(".").
                concat(extension));
        insert(studentEntity);

        filesStorageService.save(paymentPhoto, receiptUrl);

    }

    @Transactional
    public void activeStudentCourse(Long personId, Long courseId) {
        try {
            emailService.sendEmail(
                    personRepository.getReferenceById(personId).getEmail(),
                    "Confirmación de aprobación de curso",
                    "Estimado/a estudiante,\n\nNos complace informarle que su solicitud para el curso ha sido aprobada por el profesor.\n\nYa puede acceder al contenido del curso y comenzar su formación en Prad Academy.\n\nSi tiene alguna duda o necesita asistencia, no dude en contactarnos.\n\n¡Le deseamos mucho éxito en su aprendizaje!\n\nAtentamente,\nEl equipo de Prad Academy",
                    null,null
            );

            log.info("Correo enviado correctamente de activar.");
        } catch (Exception e) {
            log.warn("No se pudo enviar el correo de activar: " + e.getMessage());
            // Opcional: registrar en BD o sistema de alertas
        }
        StudentCourseEntity studentEntity = getById(courseId);
        studentEntity.setStatus(EnumCourseStatus.ACTIVATED);
        studentEntity.setStartDate(LocalDate.now().plusDays(1));
        studentEntity.setEndDate(LocalDate.now().plusDays(studentEntity.getCourse().getDurationDays() + 1));
        StudentCourseEntity studentCourseinsert = insert(studentEntity);


        List<ConfigModuleEntity> moduleEntities = configModuleService.getModuleByCourse(studentEntity.getCourse().getId());
        for (ConfigModuleEntity moduleEntity : moduleEntities) {
            StudentModuleEntity entityModuleTemp = new StudentModuleEntity();
            entityModuleTemp.setStudentCourse(studentCourseinsert);
            entityModuleTemp.setModule(moduleEntity);
            if (moduleEntity.getOrderNum() == 0)
                entityModuleTemp.setStatus(EnumModuleStatus.APPROVED);
            else
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
    }

    @Transactional
    public void rejectCourse(long personId, long courseId) {
        studentCourserepository.updateStatusById(courseId, EnumCourseStatus.REJECTED);
        try {
            emailService.sendEmail(
                    personRepository.getReferenceById(personId).getEmail(),
                    TranslatorCode.COURSE_REJECT_TOPIC,
                    TranslatorCode.COURSE_REJECT_BODY,
                    null, null
            );
            log.info("Correo enviado correctamente rechazar.");
        } catch (Exception e) {
            log.warn("No se pudo enviar el correo de rechazar: " + e.getMessage());
            // Opcional: registrar en BD o sistema de alertas
        }
    }

    public void findExpiredActivatedCourses() {
        List<StudentCourseEntity> resultCourses = studentCourserepository.findExpiredActivatedCourses();
        for (StudentCourseEntity expiredActivatedCours : resultCourses) {
            List<EnumModuleStatus> statuses = studentModuleRepository.findStatusesByCourseId(expiredActivatedCours.getId().longValue());

            boolean allApproved = !statuses.isEmpty() && statuses.stream()
                    .allMatch(status -> status == EnumModuleStatus.APPROVED);
            if (allApproved) {
                expiredActivatedCours.setStatus(EnumCourseStatus.APPROVED);
            } else {
                expiredActivatedCours.setStatus(EnumCourseStatus.NOT_APPROVED);
            }
        }
        studentCourserepository.saveAll(resultCourses);
    }

    @Transactional
    public void uploadCertifyStudentCourse(Long personId, Long courseId,  MultipartFile certify) {
        String extension = filesStorageService.getExtension(certify);
        try {
            emailService.sendEmail(
                    personRepository.getReferenceById(personId).getEmail(),
                    TranslatorCode.COURSE_ISSUED_TOPIC,
                    TranslatorCode.COURSE_ISSUED_BODY,
                    certify.getBytes(),
                    EnumImagenType.CERTIFY.name().concat(".").concat(extension)
            );
            log.info("Correo enviado correctamente de aplicar.");
        } catch (Exception e) {
            log.warn("No se pudo enviar el correo para aplicar: " + e.getMessage());
            // Opcional: registrar en BD o sistema de alertas
        }
        String receiptUrl = EnumImagenType.CERTIFY.name().
                concat("/").
                concat(DateUtils.getCurrentDateFormat("yyyyMMddHHmmss")).
                concat("-").
                concat(EnumImagenType.CERTIFY.name()).
                concat(personId.toString());

        StudentCourseEntity studentEntity = getById(courseId);
        studentEntity.setCertifyUrl(receiptUrl.
                concat(".").
                concat(extension));
        update(courseId, studentEntity);

        filesStorageService.save(certify, receiptUrl);

    }
}