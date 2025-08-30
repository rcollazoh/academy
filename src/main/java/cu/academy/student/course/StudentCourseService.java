package cu.academy.student.course;

import cu.academy.config.course.ConfigCourseEntity;
import cu.academy.config.course.ConfigCourseService;
import cu.academy.shared.configs.text_messages.Translator;
import cu.academy.shared.enum_types.CourseStatus;
import cu.academy.shared.exceptions.ArgumentException;
import cu.academy.shared.utils.TranslatorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class StudentCourseService {
    private final StudentCourseRepository repository;
    private final ConfigCourseService configCourseService;

//    private final ModelMapper modelMapper;
//    private static final Type listType = new TypeToken<List<NomAplicacionRespRedDto>>() {
//    }.getType();

    @Autowired
    public StudentCourseService(StudentCourseRepository repository, ConfigCourseService configCourseService) {
        this.repository = repository;
        this.configCourseService = configCourseService;
    }

    public StudentCourseEntity getById(Long id) throws ArgumentException {
        return (repository.findById(id))
                .orElseThrow(() -> new ArgumentException(Translator.toLocale(TranslatorCode.NO_TIPO_APLICACION)));
    }

    public List<StudentCourseEntity> getAllSort() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    public List<StudentCourseEntity> getFindByPersonId(long personId) {
        return repository.findByPersonId(personId);
    }

    public StudentCourseEntity getStudentCourseByAreaAndPractice(long personId, long areaId, long practiceId) {
        // Define the valid statuses to check for existing enrollments
        List<CourseStatus> validStatuses = List.of(CourseStatus.PENDING, CourseStatus.ACTIVE);

        // Look for any existing student course with matching personId and valid status
        List<StudentCourseEntity> existing = repository.findByPersonIdAndStatusIn(personId, validStatuses);
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
            result.setStatus(CourseStatus.NEW); // Simulated status, it is a flag
            result.setStartDate(LocalDate.now());
        }
        return result;
    }

    @Transactional
    public void delete(Long id) throws ArgumentException {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new ArgumentException(Translator.toLocale(TranslatorCode.NO_TIPO_APLICACION));
        }
    }

    @Transactional
    public StudentCourseEntity insert(StudentCourseEntity entity) {
        return repository.save(entity);
    }

    @Transactional
    public StudentCourseEntity update(Long id, StudentCourseEntity entity) throws ArgumentException {
        if (!repository.existsById(id)) {
            throw new ArgumentException(Translator.toLocale(TranslatorCode.NO_TIPO_APLICACION));
        }
        return repository.save(entity);
    }
}