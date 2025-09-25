package cu.academy.config.exam.question;

import cu.academy.config.parameter.ConfigParameterEntity;
import cu.academy.config.parameter.ConfigParameterRepository;
import cu.academy.shared.configs.text_messages.Translator;
import cu.academy.shared.exceptions.ArgumentException;
import cu.academy.shared.utils.TranslatorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ConfigQuestionService {
    private final ConfigQuestionRepository repository;

//    private final ModelMapper modelMapper;
//    private static final Type listType = new TypeToken<List<NomAplicacionRespRedDto>>() {
//    }.getType();

    @Autowired
    public ConfigQuestionService(ConfigQuestionRepository repository) {
        this.repository = repository;
    }


}