package cu.academy.config.classes.image;

import cu.academy.shared.configs.text_messages.Translator;
import cu.academy.shared.exceptions.ArgumentException;
import cu.academy.shared.utils.TranslatorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigClassImageService {
    private final ConfigClassImageRepository repository;

    @Autowired
    public ConfigClassImageService(ConfigClassImageRepository repository) {
        this.repository = repository;
    }

    public ConfigClassImageEntity getById(Long id) throws ArgumentException {
        return (repository.findById(id))
                .orElseThrow(() -> new ArgumentException(Translator.toLocale(TranslatorCode.NO_TIPO_APLICACION)));
    }

    public List<ConfigClassImageEntity> getAllSort() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

//    public List<ConfigClassEntity> getClassByModule(long moduleId) {
//        return repository.findClassesByModule(moduleId);
//    }

}