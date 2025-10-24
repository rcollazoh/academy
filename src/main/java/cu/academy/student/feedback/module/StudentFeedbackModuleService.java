package cu.academy.student.feedback.module;

import cu.academy.shared.configs.text_messages.Translator;
import cu.academy.shared.exceptions.ArgumentException;
import cu.academy.shared.utils.TranslatorCode;
import cu.academy.student.feedback.mapper.StudentFeedbackMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentFeedbackModuleService {
    private final StudentFeedbackModuleRepository repository;
    private final StudentFeedbackMapper mapper;

    @Autowired
    public StudentFeedbackModuleService(StudentFeedbackModuleRepository repository, StudentFeedbackMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public StudentFeedbackModuleEntity insert(StudentFeedbackModuleEntity entity) {
        return repository.save(entity);
    }

    public StudentFeedbackModuleEntity getById(Long id) throws ArgumentException {
        return (repository.findById(id))
                .orElseThrow(() -> new ArgumentException(Translator.toLocale(TranslatorCode.NO_EXISTE_ELEMENT)));
    }
}