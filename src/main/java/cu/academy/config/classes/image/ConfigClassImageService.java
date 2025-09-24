package cu.academy.config.classes.image;

import cu.academy.config.classes.image.dto.ClassImageNavigationDto;
import cu.academy.config.classes.image.dto.ClassImageNavigationView;
import cu.academy.config.classes.image.mapper.ConfigClassImageMapper;
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
    private final ConfigClassImageMapper mapper;

    @Autowired
    public ConfigClassImageService(ConfigClassImageRepository repository, ConfigClassImageMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
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
    public ClassImageNavigationDto getClassWithNavigation(Long classId) {
        ClassImageNavigationView view = repository.findNavigationByIdClass(classId);
        return mapper.toDto(view);
    }

    public ClassImageNavigationDto getImageWithNavigation(Long classId, Long id) {
        ClassImageNavigationView view = repository.findNavigationById(classId, id);
        return mapper.toDto(view);
    }
}