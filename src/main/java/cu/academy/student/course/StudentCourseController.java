package cu.academy.student.course;

import cu.academy.student.course.dto.StudentCourseDto;
import cu.academy.student.course.mapper.StudentCourseMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<StudentCourseDto> getAll() {
        return service.getAllSort()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-person/{personId}")
    public List<StudentCourseDto> getAllStudentCourseByPerson(@PathVariable Long personId) {
        return service.getFindByPersonId(personId)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentCourseDto> getById(@PathVariable Long id) {
        StudentCourseEntity person = service.getById(id);
        return person != null ? ResponseEntity.ok(mapper.toDto(person)) : ResponseEntity.notFound().build();
    }

    @GetMapping("by-person/{personId}/by-area/{areaId}/by-practice/{practiceId}")
    public ResponseEntity<StudentCourseDto> getStudentCourseByPersonByAreaAndPractice(
            @PathVariable Long personId,
            @PathVariable Long areaId,
            @PathVariable Long practiceId) {
        StudentCourseEntity courseResponse = service.getStudentCourseByAreaAndPractice(personId, areaId, practiceId);
        return courseResponse != null ? ResponseEntity.ok(mapper.toDto(courseResponse)) : ResponseEntity.notFound().build();
    }

    @PostMapping("apply, consumes = multipart/form-data")
    public ResponseEntity<StudentCourseDto> applyStudentCourse(@RequestParam("personId") Long personId,
                                                               @RequestParam("courseId") Long courseId,
                                                               @RequestParam("paymentMethod") Long paymentMethod,
                                                               @RequestParam(value = "payment") MultipartFile paymentPhoto) {
service.applyStudentCourse( personId, courseId, paymentMethod, paymentPhoto);
        return null;
    }
}