package cu.academy.student.classes;

import cu.academy.shared.configs.text_messages.Translator;
import cu.academy.shared.enum_types.EnumModuleStatus;
import cu.academy.shared.exceptions.ArgumentException;
import cu.academy.shared.utils.TranslatorCode;
import cu.academy.student.module.StudentModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentClassService {
    private final StudentClassRepository repository;
    private final StudentModuleRepository repositoryModule;

//    private final ModelMapper modelMapper;
//    private static final Type listType = new TypeToken<List<NomAplicacionRespRedDto>>() {
//    }.getType();

    @Autowired
    public StudentClassService(StudentClassRepository repository, StudentModuleRepository repositoryModule) {
        this.repository = repository;
        this.repositoryModule = repositoryModule;
    }

    @Transactional
    public StudentClassEntity insert(StudentClassEntity entity) {
        return repository.save(entity);
    }

    @Transactional
    public void updateViewedAndCurrentImageId(Long id, boolean status, Long currentImageId) {
        if (status) {
            repository.updateViewedById(id, status);
        }
        repository.updateCurrentImageIdById(id, currentImageId);
        StudentClassEntity classEntity = getById(id);

        if ("Clase introductoria del curso".equals(classEntity.getConfigClass().getDescription()))
            repositoryModule.updateStatusById(classEntity.getStudentModule().getId(), EnumModuleStatus.APPROVED);
    }

    public StudentClassEntity getById(Long id) throws ArgumentException {
        return (repository.findById(id))
                .orElseThrow(() -> new ArgumentException(Translator.toLocale(TranslatorCode.NO_EXISTE_ELEMENT)));
    }

    public List<StudentClassEntity> getAllSort() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    public List<StudentClassEntity> getAllByModuleId(long classId) {
        return repository.findByModuleId(classId);
    }
}