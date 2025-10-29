package cu.academy.config.reference;

import cu.academy.shared.configs.text_messages.Translator;
import cu.academy.shared.exceptions.ArgumentException;
import cu.academy.shared.utils.TranslatorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ConfigReferenceService {
    private final ConfigReferenceRepository repository;

    @Autowired
    public ConfigReferenceService(ConfigReferenceRepository repository) {
        this.repository = repository;
    }

    public ConfigReferenceEntity getById(Long id) throws ArgumentException {
        return (repository.findById(id))
                .orElseThrow(() -> new ArgumentException(Translator.toLocale(TranslatorCode.NO_EXISTE_ELEMENT)));
    }

    public List<ConfigReferenceEntity> getAllReferenceByModuleId(Long moduleId) throws ArgumentException {
        return repository.findByModuleAndOrderByOrderNumAsc(moduleId);
    }

    public List<ConfigReferenceEntity> getAllSort() {
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
    public ConfigReferenceEntity insert(ConfigReferenceEntity entity) {
        return repository.save(entity);
    }

    @Transactional
    public ConfigReferenceEntity update(Long id, ConfigReferenceEntity entity) throws ArgumentException {
        if (!repository.existsById(id)) {
            throw new ArgumentException(Translator.toLocale(TranslatorCode.NO_EXISTE_ELEMENT));
        }
        return repository.save(entity);
    }
}