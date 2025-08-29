package cu.academy.config.course;

import cu.academy.nom.area.NomAreaEntity;
import cu.academy.nom.area.NomAreaRepository;
import cu.academy.nom.practice.NomPracticeEntity;
import cu.academy.shared.configs.text_messages.Translator;
import cu.academy.shared.exceptions.ArgumentException;
import cu.academy.shared.utils.TranslatorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ConfigCourseService {
    private final ConfigCourseRepository repository;

//    private final ModelMapper modelMapper;
//    private static final Type listType = new TypeToken<List<NomAplicacionRespRedDto>>() {
//    }.getType();

    @Autowired
    public ConfigCourseService(ConfigCourseRepository repository) {
        this.repository = repository;
    }

    public ConfigCourseEntity getById(Long id) throws ArgumentException {
        return (repository.findById(id))
                .orElseThrow(() -> new ArgumentException(Translator.toLocale(TranslatorCode.NO_TIPO_APLICACION)));
    }

    public List<ConfigCourseEntity> getAllSort() {
        return repository.findByActiveTrue(Sort.by(Sort.Direction.ASC, "name"));
    }

    public ConfigCourseEntity getCourseByAreaAndPractice(long areaId,long practiceId) {
        return repository.findByAreaIdAndPracticeId(areaId, practiceId);
    }

    @Transactional
    public void delete(Long id) throws ArgumentException {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new ArgumentException(Translator.toLocale(TranslatorCode.NO_TIPO_APLICACION));
        }
    }

    @Transactional
    public ConfigCourseEntity insert(ConfigCourseEntity entity) {
        return repository.save(entity);
    }

    @Transactional
    public ConfigCourseEntity update(Long id, ConfigCourseEntity entity) throws ArgumentException {
        if (!repository.existsById(id)) {
            throw new ArgumentException(Translator.toLocale(TranslatorCode.NO_TIPO_APLICACION));
        }
        return repository.save(entity);
    }
}