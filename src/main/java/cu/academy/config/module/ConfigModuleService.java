package cu.academy.config.module;

import cu.academy.shared.configs.text_messages.Translator;
import cu.academy.shared.exceptions.ArgumentException;
import cu.academy.shared.utils.TranslatorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ConfigModuleService {
    private final ConfigModuleRepository repository;

//    private final ModelMapper modelMapper;
//    private static final Type listType = new TypeToken<List<NomAplicacionRespRedDto>>() {
//    }.getType();

    @Autowired
    public ConfigModuleService(ConfigModuleRepository repository) {
        this.repository = repository;
    }

    public ConfigModuleEntity getById(Long id) throws ArgumentException {
        return (repository.findById(id))
                .orElseThrow(() -> new ArgumentException(Translator.toLocale(TranslatorCode.NO_EXISTE_ELEMENT)));
    }

    public List<ConfigModuleEntity> getAllSort() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    public List<ConfigModuleEntity> getModuleByCourse(long courseId) {
        return repository.findModulesByCourse(courseId);
    }

    @Transactional
    public void delete(Long id) throws ArgumentException {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new ArgumentException(Translator.toLocale(TranslatorCode.NO_EXISTE_ELEMENT));
        }
    }

    @Transactional
    public ConfigModuleEntity insert(ConfigModuleEntity entity) {
        return repository.save(entity);
    }

    @Transactional
    public ConfigModuleEntity update(Long id, ConfigModuleEntity entity) throws ArgumentException {
        if (!repository.existsById(id)) {
            throw new ArgumentException(Translator.toLocale(TranslatorCode.NO_EXISTE_ELEMENT));
        }
        return repository.save(entity);
    }
}