package cu.academy.student.feedback;

import cu.academy.config.module.ConfigModuleRepository;
import cu.academy.shared.configs.text_messages.Translator;
import cu.academy.shared.enum_types.EnumExamStatus;
import cu.academy.shared.exceptions.ArgumentException;
import cu.academy.shared.utils.TranslatorCode;
import cu.academy.student.feedback.dto.StudentFeedbackDto;
import cu.academy.student.feedback.mapper.StudentFeedbackMapper;
import cu.academy.student.feedback.module.StudentFeedbackModuleEntity;
import cu.academy.student.feedback.module.StudentFeedbackModuleRepository;
import cu.academy.student.feedback.module.dto.StudentFeedbackModuleDto;
import cu.academy.student.feedback.module.mapper.StudentFeedbackModuleMapper;
import cu.academy.student.module.StudentModuleEntity;
import cu.academy.student.module.StudentModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentFeedbackService {
    private final StudentFeedbackRepository repository;
    private final StudentFeedbackModuleRepository repositoryFeedbackModule;
    private final StudentModuleService serviceModule;
    private final ConfigModuleRepository repositoryConfigModule;
    private final StudentFeedbackMapper mapper;
    private final StudentFeedbackModuleMapper mapperModule;

    @Autowired
    public StudentFeedbackService(StudentFeedbackRepository repository, StudentFeedbackModuleRepository repositoryFeedbackModule, StudentModuleService serviceModule, ConfigModuleRepository repositoryConfigModule, StudentFeedbackMapper mapper, StudentFeedbackModuleMapper mapperModule) {
        this.repository = repository;
        this.repositoryFeedbackModule = repositoryFeedbackModule;
        this.serviceModule = serviceModule;
        this.repositoryConfigModule = repositoryConfigModule;
        this.mapper = mapper;
        this.mapperModule = mapperModule;
    }

    @Transactional
    public StudentFeedbackEntity insert(StudentFeedbackEntity entity) {
        return repository.save(entity);
    }

    public StudentFeedbackEntity getById(Long id) throws ArgumentException {
        return (repository.findById(id))
                .orElseThrow(() -> new ArgumentException(Translator.toLocale(TranslatorCode.NO_EXISTE_ELEMENT)));
    }

    /**
     * Inserts feedback for a course module.
     * @param studentFeedbackDto DTO containing feedback and module evaluations.
     * @throws ArgumentException if module references are invalid.
     */
    public void insertFeedBackByStudentModule(StudentFeedbackDto studentFeedbackDto) throws ArgumentException {
        StudentModuleEntity studentModuleEntity = serviceModule.getById(studentFeedbackDto.StudentModuleId());
        StudentFeedbackEntity feedbackEntity = mapper.toEntity(studentFeedbackDto);
        feedbackEntity.setModule(studentModuleEntity);

        StudentFeedbackEntity savedFeedback = insert(feedbackEntity);

        List<StudentFeedbackModuleEntity> feedbackModuleEntities = new ArrayList<>();
        for (StudentFeedbackModuleDto feedbackModuleDto: studentFeedbackDto.modules()){
            StudentFeedbackModuleEntity moduleEntity = mapperModule.toEntity(feedbackModuleDto);
            moduleEntity.setFeedback(savedFeedback);
            moduleEntity.setModule(repositoryConfigModule.getReferenceById(feedbackModuleDto.configModuleId()));

            feedbackModuleEntities.add(moduleEntity);
        }
        repositoryFeedbackModule.saveAll(feedbackModuleEntities);

        serviceModule.updateModuleAndEvaluateCourse(studentModuleEntity, EnumExamStatus.APPROVED);
    }
}