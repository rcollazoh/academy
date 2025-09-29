package cu.academy.config.classes;

import cu.academy.shared.configs.text_messages.Translator;
import cu.academy.shared.exceptions.ArgumentException;
import cu.academy.shared.utils.TranslatorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ConfigClassService {
    private final ConfigClassRepository repository;

//    private final ModelMapper modelMapper;
//    private static final Type listType = new TypeToken<List<NomAplicacionRespRedDto>>() {
//    }.getType();

    @Autowired
    public ConfigClassService(ConfigClassRepository repository) {
        this.repository = repository;
    }

    public ConfigClassEntity getById(Long id) throws ArgumentException {
        return (repository.findById(id))
                .orElseThrow(() -> new ArgumentException(Translator.toLocale(TranslatorCode.NO_EXISTE_ELEMENT)));
    }

    public List<ConfigClassEntity> getAllSort() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    public List<ConfigClassEntity> getClassByModule(long moduleId) {
        return repository.findClassesByModule(moduleId);
    }

    @Transactional
    public ConfigClassEntity update(Long id, ConfigClassEntity entity) throws ArgumentException {
        if (!repository.existsById(id)) {
            throw new ArgumentException(Translator.toLocale(TranslatorCode.NO_EXISTE_ELEMENT));
        }
        return repository.save(entity);
    }
}