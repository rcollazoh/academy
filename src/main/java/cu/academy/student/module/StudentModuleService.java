package cu.academy.student.module;

import cu.academy.config.exam.ConfigExamEntity;
import cu.academy.config.course.ConfigCourseService;
import cu.academy.config.exam.ConfigExamRepository;
import cu.academy.shared.configs.text_messages.Translator;
import cu.academy.shared.exceptions.ArgumentException;
import cu.academy.shared.utils.TranslatorCode;
import cu.academy.student.classes.StudentClassEntity;
import cu.academy.student.classes.StudentClassService;
import cu.academy.student.classes.dto.StudentClassDto;
import cu.academy.student.exam.StudentExamEntity;
import cu.academy.student.exam.StudentExamRepository;
import cu.academy.student.exam.dto.StudentExamDto;
import cu.academy.student.module.dto.StudentModuleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentModuleService {
    private final StudentModuleRepository repository;
    private final StudentExamRepository examRepository;
    private final StudentClassService studentClassService;
    private final ConfigCourseService configCourseService;

//    private final ModelMapper modelMapper;
//    private static final Type listType = new TypeToken<List<NomAplicacionRespRedDto>>() {
//    }.getType();

    @Autowired
    public StudentModuleService(StudentModuleRepository repository, StudentExamRepository examRepository, StudentClassService studentClassService, ConfigCourseService configCourseService) {
        this.repository = repository;
        this.examRepository = examRepository;
        this.studentClassService = studentClassService;
        this.configCourseService = configCourseService;
    }

    public StudentModuleEntity getById(Long id) throws ArgumentException {
        return (repository.findById(id))
                .orElseThrow(() -> new ArgumentException(Translator.toLocale(TranslatorCode.NO_TIPO_APLICACION)));
    }

    public List<StudentModuleEntity> getAllSort() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    public List<StudentModuleDto> getAllModuleByCourseId(Long courseId) {
        List<StudentModuleDto> result = new ArrayList<>();
        List<StudentModuleEntity> modules = repository.findFullModulesByStudentCourseId(courseId);
        for (StudentModuleEntity module : modules) {
            List<StudentClassEntity> classes = studentClassService.getAllByModuleId(module.getId());
            Optional<StudentExamEntity> exam = examRepository.findByConfigModuleId(module.getId());

            // 4. Mapear clases y examen a sus respectivos DTOs
            List<StudentClassDto> classDtos = new ArrayList<>();
            for (StudentClassEntity classEntity : classes) {
                classDtos.add(new StudentClassDto(classEntity.getId(),
                        classEntity.getViewed(), classEntity.getClassField().getId(),
                        classEntity.getClassField().getTitle(), classEntity.getClassField().getType(), classEntity.getClassField().getRecourseUrl()));
            }

            StudentExamDto examDto = null;
            if (exam.isPresent()) {
                examDto = new StudentExamDto(exam.get().getId(), exam.get().getViewed(), exam.get().getStatus(),
                        exam.get().getConfigExam().getId(), exam.get().getConfigExam().getTitle(), exam.get().getConfigExam().getRecourseUrl());
            } ;

            // 5. Construir el DTO del módulo
            StudentModuleDto dto = new StudentModuleDto(
                    module.getId(),
                    module.getModule().getId(), // Asegúrate que este getter exista
                    module.getStatus(),
                    module.getModule().getName(),
                    module.getModule().getOrderNum(),
                    classDtos,
                    examDto
            );
            result.add(dto);
        }
        return result;
    }
}