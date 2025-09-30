package cu.academy.config.exam.question;

import cu.academy.config.exam.option.ConfigOptionService;
import cu.academy.config.exam.question.dto.ExamQuestionOptionsDto;
import cu.academy.config.exam.question.mapper.ConfigQuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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

    public List<ExamQuestionOptionsDto> getExamWithQuestionAndOptions(Long examId, Long totalQuestions) {
        List<ConfigQuestionEntity> configQuestionEntityList = repository.findRandomQuestions(examId, PageRequest.of(0, Math.toIntExact(totalQuestions)));
        return mapper.toDtoList(configQuestionEntityList);
    }
}