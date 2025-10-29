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

    @PostMapping
    public void insertFeedBackByStudentModule(@RequestBody StudentFeedbackDto studentFeedbackDto) throws ArgumentException {
        service.insertFeedBackByStudentModule(studentFeedbackDto);
    }
}