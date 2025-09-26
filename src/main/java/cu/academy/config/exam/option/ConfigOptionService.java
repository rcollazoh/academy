package cu.academy.config.exam.option;

import cu.academy.config.exam.option.dto.ConfigOptionDto;
import cu.academy.config.exam.option.mapper.ConfigOptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConfigOptionService {
    private final ConfigOptionRepository repository;

    private final ConfigOptionMapper mapper;
//    private static final Type listType = new TypeToken<List<NomAplicacionRespRedDto>>() {
//    }.getType();

    @Autowired
    public ConfigOptionService(ConfigOptionRepository repository, ConfigOptionMapper modelMapper) {
        this.repository = repository;
        this.mapper = modelMapper;
    }


    public List<ConfigOptionDto> getOptionsByQuestion(Long questionId) {
        List<ConfigOptionEntity> result = repository.findByQuestionId(questionId);
        return result.stream().map(mapper::toDto).collect(Collectors.toList());
    }
}