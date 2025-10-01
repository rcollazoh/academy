package cu.academy.student.exam;

import cu.academy.shared.configs.text_messages.Translator;
import cu.academy.shared.enum_types.EnumExamStatus;
import cu.academy.shared.exceptions.ArgumentException;
import cu.academy.shared.utils.TranslatorCode;
import cu.academy.student.exam.dto.StudentExamRequestDto;
import cu.academy.student.exam.dto.StudentExamResponseDto;
import cu.academy.student.module.StudentModuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class StudentExamService {
    private static final Logger log = LoggerFactory.getLogger(StudentExamService.class);
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
    public StudentExamResponseDto updateStatusAndModule(Long id, List<StudentExamRequestDto> requestExam) {

        StudentExamEntity examEntity = getById(id); // Ej: devuelve 3

        // count the approved.
        long approvedCount = requestExam.stream()
                .filter(StudentExamRequestDto::isCorrect)
                .count();

        // check logic
        EnumExamStatus examStatus = approvedCount >= examEntity.getConfigExam().getMinQuestions() ? EnumExamStatus.APPROVED : EnumExamStatus.NOT_APPROVED;

        // update exam
        updateStatus(id, examStatus);

        //update module and course
        EnumExamStatus courseStatus = studentModuleService.updateModuleAndEvaluateCourse(getById(id).getStudentModule(), examStatus);
        return new StudentExamResponseDto(examStatus,courseStatus);
    }

    @Transactional
    public StudentExamEntity insert(StudentExamEntity entity) {
        return repository.save(entity);
    }


    public StudentExamEntity getById(Long id) throws ArgumentException {
        return (repository.findById(id))
                .orElseThrow(() -> new ArgumentException(Translator.toLocale(TranslatorCode.NO_EXISTE_ELEMENT)));
    }

    public List<StudentExamEntity> getAllSort() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }
}