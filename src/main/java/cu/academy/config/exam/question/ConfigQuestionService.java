package cu.academy.config.exam.question;

import cu.academy.config.exam.option.ConfigOptionService;
import cu.academy.config.exam.option.dto.ConfigOptionDto;
import cu.academy.config.exam.question.dto.ExamQuestionNavigationDto;
import cu.academy.config.exam.question.dto.ExamQuestionNavigationView;
import cu.academy.config.exam.question.mapper.ConfigQuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigQuestionService {
    private final ConfigQuestionRepository repository;
    private final ConfigOptionService optionService;

    private final ConfigQuestionMapper mapper;
//    private static final Type listType = new TypeToken<List<NomAplicacionRespRedDto>>() {
//    }.getType();

    @Autowired
    public ConfigQuestionService(ConfigQuestionRepository repository, ConfigOptionService optionService, ConfigQuestionMapper modelMapper) {
        this.repository = repository;
        this.optionService = optionService;
        this.mapper = modelMapper;
    }


    public ExamQuestionNavigationDto getExamWithNavigation(Long examId) {
        ExamQuestionNavigationView view = repository.findNavigationByIdExam(examId);
        ExamQuestionNavigationDto result = mapper.toDto(view);
        return result.withOptionDto(optionService.getOptionsByQuestion(view.getCurrentId()));
    }
}