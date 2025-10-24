package cu.academy.student.feedback;

import cu.academy.shared.exceptions.ArgumentException;
import cu.academy.student.feedback.dto.StudentFeedbackDto;
import cu.academy.student.feedback.mapper.StudentFeedbackMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/academy/student_feedback")
public class StudentFeedbackController {

    private final StudentFeedbackService service;
    private final StudentFeedbackMapper mapper;

    public StudentFeedbackController(StudentFeedbackService service, StudentFeedbackMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<StudentClassDto> getById(@PathVariable Long id) {
//        StudentFeedbackEntity entity = service.getById(id);
//        return entity != null ? ResponseEntity.ok(mapper.toDto(entity)) : ResponseEntity.notFound().build();
//    }

    @PostMapping
    public void insertFeedBackByStudentModule(@RequestBody StudentFeedbackDto studentFeedbackDto) throws ArgumentException {
        service.insertFeedBackByStudentModule(studentFeedbackDto);
    }
}