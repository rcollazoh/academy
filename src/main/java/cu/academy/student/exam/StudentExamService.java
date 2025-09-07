package cu.academy.student.exam;

import cu.academy.shared.configs.text_messages.Translator;
import cu.academy.shared.enum_types.EnumExamStatus;
import cu.academy.shared.enum_types.EnumModuleStatus;
import cu.academy.shared.exceptions.ArgumentException;
import cu.academy.shared.utils.TranslatorCode;
import cu.academy.student.classes.StudentClassService;
import cu.academy.student.module.StudentModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class StudentExamService {
    private final StudentExamRepository repository;
    private final StudentModuleService studentModuleService;

//    private final ModelMapper modelMapper;
//    private static final Type listType = new TypeToken<List<NomAplicacionRespRedDto>>() {
//    }.getType();

    @Autowired
    public StudentExamService(StudentExamRepository repository, StudentModuleService studentModuleService) {
        this.repository = repository;
        this.studentModuleService = studentModuleService;
    }

    @Transactional
    public void updateStatus(Long id, EnumExamStatus status) {
        repository.updateStatusById(id, status);
    }

    @Transactional
    public void updateStatusAndModule(Long id, EnumExamStatus status) {
        updateStatus(id, status);
        studentModuleService.updateModuleAndEvaluateCourse(getById(id).getStudentModule(), status);
    }

    @Transactional
    public StudentExamEntity insert(StudentExamEntity entity) {
        return repository.save(entity);
    }


    public StudentExamEntity getById(Long id) throws ArgumentException {
        return (repository.findById(id))
                .orElseThrow(() -> new ArgumentException(Translator.toLocale(TranslatorCode.NO_TIPO_APLICACION)));
    }

    public List<StudentExamEntity> getAllSort() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }
}