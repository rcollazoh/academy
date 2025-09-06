package cu.academy.student.exam;

import cu.academy.shared.configs.text_messages.Translator;
import cu.academy.shared.enum_types.EnumExamStatus;
import cu.academy.shared.enum_types.EnumModuleStatus;
import cu.academy.shared.exceptions.ArgumentException;
import cu.academy.shared.utils.TranslatorCode;
import cu.academy.student.classes.StudentClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class StudentExamService {
    private final StudentExamRepository repository;
    private final StudentExamRepository examRepository;
    private final StudentClassService studentClassService;

//    private final ModelMapper modelMapper;
//    private static final Type listType = new TypeToken<List<NomAplicacionRespRedDto>>() {
//    }.getType();

    @Autowired
    public StudentExamService(StudentExamRepository repository, StudentExamRepository examRepository, StudentClassService studentClassService) {
        this.repository = repository;
        this.examRepository = examRepository;
        this.studentClassService = studentClassService;
    }

    @Transactional
    public void updateStatus(Long id, EnumExamStatus status) {
        repository.updateStatusById(id, status);
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