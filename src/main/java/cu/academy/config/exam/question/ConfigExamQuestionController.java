package cu.academy.config.exam.question;


import cu.academy.config.exam.question.dto.ExamQuestionNavigationDto;
import cu.academy.config.exam.question.dto.ExamQuestionOptionsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/academy/config_exam_question")
public class ConfigExamQuestionController {

    private final ConfigQuestionService service;

    public ConfigExamQuestionController(ConfigQuestionService service) {
        this.service = service;
    }

    @GetMapping("/exam_navegation/{examId}")
    public ResponseEntity<ExamQuestionNavigationDto> getClassWithNavigation(@PathVariable Long examId) {
        ExamQuestionNavigationDto result = service.getExamWithNavigation(examId);
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{examId}")
    public List<ExamQuestionOptionsDto> getExamWithQuestionAndOptions(@PathVariable Long examId) {
        return service.getExamWithQuestionAndOptions(examId);
    }
}