package cu.academy.nom.practice;


import cu.academy.shared.configs.text_messages.Translator;
import cu.academy.shared.exceptions.ArgumentException;
import cu.academy.shared.utils.TranslatorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NomPracticeService {
    private final NomPracticeRepository repository;

//    private final ModelMapper modelMapper;
//    private static final Type listType = new TypeToken<List<NomAplicacionRespRedDto>>() {
//    }.getType();

    @Autowired
    public NomPracticeService(NomPracticeRepository repository) {
        this.repository = repository;
    }

    public NomPracticeEntity getById(Long id) throws ArgumentException {
        return (repository.findById(id))
                .orElseThrow(() -> new ArgumentException(Translator.toLocale(TranslatorCode.NO_EXISTE_ELEMENT)));
    }

    public List<NomPracticeEntity> getAllSort() {
        return repository.findByActiveTrue(Sort.by(Sort.Direction.ASC, "name"));
    }

    public List<NomPracticeEntity> getAllSortByArea(Long areaId) {
        return repository.findByAreaIdAndActiveTrueOrderByNameAsc(areaId);
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
    public NomPracticeEntity insert(NomPracticeEntity entity) {
        return repository.save(entity);
    }

    @Transactional
    public NomPracticeEntity update(Long id, NomPracticeEntity entity) throws ArgumentException {
        if (!repository.existsById(id)) {
            throw new ArgumentException(Translator.toLocale(TranslatorCode.NO_EXISTE_ELEMENT));
        }
        return repository.save(entity);
    }
}