package cu.academy.config.exam.question;


import cu.academy.config.exam.question.dto.ExamQuestionOptionsDto;
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

    @GetMapping("/{examId}/{totalQuestions}")
    public List<ExamQuestionOptionsDto> getExamWithQuestionAndOptions(@PathVariable Long examId, @PathVariable Long totalQuestions) {
        return service.getExamWithQuestionAndOptions(examId,totalQuestions);
    }
}