package cu.academy.student.course;

import cu.academy.shared.dto.PagingResponseList;
import cu.academy.shared.enum_types.EnumPaymentMethod;
import cu.academy.shared.utils.EndpointResult;
import cu.academy.student.course.dto.StudentCourseDto;
import cu.academy.student.course.dto.StudentCourseForMyselfDto;
import cu.academy.student.course.mapper.StudentCourseMapper;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Join;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/academy/student_course")
public class StudentCourseController {

    private final StudentCourseService service;
    private final StudentCourseMapper mapper;

    public StudentCourseController(StudentCourseService service, StudentCourseMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<EndpointResult> getCourses(
            @Join(path = "course", alias = "configCourse")
            @And({
                    @Spec(path = "status", params = "status", spec = Equal.class),
                    @Spec(path = "configCourse.id", params = "courseId", spec = Equal.class)
            }) Specification<StudentCourseEntity> statusSpec,
//            @Or({
//            @Spec(path = "name", params = "search", spec = Like.class)
//            }) Specification<StudentCourseEntity> nameSpec,

            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
            Sort sort
    ) {
        try {
//            Specification<StudentCourseEntity> spec = Specification.allOf(statusSpec);

            Specification<StudentCourseEntity> spec = Stream.of(statusSpec)
                    .filter(Objects::nonNull)
                    .reduce(Specification::and)
                    .orElse(null);


            PagingResponseList<StudentCourseEntity> pagingResponseCourses = service.getAll(spec, pageNumber, pageSize, sort);

            PagingResponseList<StudentCourseDto> pagingResponseDtos = new PagingResponseList<>(
                    pagingResponseCourses.getCount(),
                    pagingResponseCourses.getPageNumber(),
                    pagingResponseCourses.getPageSize(),
                    pagingResponseCourses.getPageOffset(),
                    pagingResponseCourses.getPageTotal(),
                    pagingResponseCourses.getElements().stream().map(mapper::toDto).collect(Collectors.toList())
            );

            return ResponseEntity.ok(new EndpointResult(pagingResponseDtos, null));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new EndpointResult(null, ex.getMessage()));
        }
    }

    @GetMapping("/by-person/{personId}")
    public List<StudentCourseForMyselfDto> getAllStudentCourseByPerson(@PathVariable Long personId) {
        return service.getFindByPersonId(personId)
                .stream()
                .map(mapper::toDtoMyself)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentCourseDto> getById(@PathVariable Long id) {
        StudentCourseEntity person = service.getById(id);
        return person != null ? ResponseEntity.ok(mapper.toDto(person)) : ResponseEntity.notFound().build();
    }

    @GetMapping("/active-by-person/{personId}")
    public ResponseEntity<StudentCourseDto> getStudentCourseActiveByPerson(
            @PathVariable Long personId) {
        StudentCourseEntity courseResponse = service.getStudentCourseActiveByPerson(personId);
        return courseResponse != null ? ResponseEntity.ok(mapper.toDto(courseResponse)) : ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/apply", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<StudentCourseDto> applyStudentCourse(@RequestParam("personId") Long personId,
                                                               @RequestParam("courseId") Long courseId,
                                                               @RequestParam("paymentMethod") EnumPaymentMethod paymentMethod,
                                                               @RequestParam(value = "payment") MultipartFile paymentPhoto) {
        service.applyStudentCourse(personId, courseId, paymentMethod, paymentPhoto);
        return null;
    }

    @PostMapping(value = "/active")
    public ResponseEntity<StudentCourseDto> activeStudentCourse(@RequestParam("personId") Long personId,
                                                               @RequestParam("courseId") Long courseId) {
        service.activeStudentCourse(personId, courseId);
        return null;
    }

    @PostMapping(value = "/reject")
    public ResponseEntity<StudentCourseDto> rejectStudentCourse(@RequestParam("personId") Long personId,
                                                                @RequestParam("courseId") Long courseId) {
        service.rejectCourse(personId, courseId);
        return null;
    }

    @PostMapping(value = "/upload_certify", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<StudentCourseDto> uploadCertifyStudentCourse(@RequestParam("personId") Long personId,
                                                               @RequestParam("courseId") Long courseId,
                                                               @RequestParam(value = "certify") MultipartFile certify) {
        service.uploadCertifyStudentCourse(personId, courseId, certify);
        return null;
    }

}