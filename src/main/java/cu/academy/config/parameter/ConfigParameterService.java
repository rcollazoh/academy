package cu.academy.config.parameter;

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
public class ConfigParameterService {
    private final ConfigParameterRepository repository;

    @Autowired
    public ConfigParameterService(ConfigParameterRepository repository) {
        this.repository = repository;
    }

    public ConfigParameterEntity getById(Long id) throws ArgumentException {
        return (repository.findById(id))
                .orElseThrow(() -> new ArgumentException(Translator.toLocale(TranslatorCode.NO_EXISTE_ELEMENT)));
    }

    public List<ConfigParameterEntity> getAllSort() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "name"));
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
    public ConfigParameterEntity insert(ConfigParameterEntity entity) {
        return repository.save(entity);
    }

    @Transactional
    public ConfigParameterEntity update(Long id, ConfigParameterEntity entity) throws ArgumentException {
        if (!repository.existsById(id)) {
            throw new ArgumentException(Translator.toLocale(TranslatorCode.NO_EXISTE_ELEMENT));
        }
        return repository.save(entity);
    }

    @Cacheable(value = "configParameter", key = "#name")
    public ConfigParameterEntity getBy(String name) throws ArgumentException {
        if (name == null || name.trim().isEmpty()) {
            throw new ArgumentException(Translator.toLocale(TranslatorCode.NO_EXISTE_ELEMENT));
        }

        return repository.findByName(name)
                .orElseThrow(() -> new ArgumentException(
                        String.format(Translator.toLocale(TranslatorCode.NO_PARAMETER_X), name)));
    }
}