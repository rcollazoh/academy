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
    public void updateViewed(Long id, boolean status) {
        repository.updateViewedById(id, status);

        StudentClassEntity classEntity = getById(id);

        boolean isCommonModule = repositoryModule.findModuleByIdCommon(classEntity.getStudentModule().getId());

        if (isCommonModule)
            repositoryModule.updateStatusById(classEntity.getStudentModule().getId(), EnumModuleStatus.APPROVED);
    }

    public StudentClassEntity getById(Long id) throws ArgumentException {
        return (repository.findById(id))
                .orElseThrow(() -> new ArgumentException(Translator.toLocale(TranslatorCode.NO_TIPO_APLICACION)));
    }

    public List<StudentClassEntity> getAllSort() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    public List<StudentClassEntity> getAllByModuleId(long classId) {
        return repository.findByModuleId(classId);
    }
}