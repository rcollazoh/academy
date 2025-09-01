package cu.academy.student.module;

import cu.academy.student.module.dto.StudentModuleDto;
import cu.academy.student.module.dto.StudentModuleSimpleDto;
import cu.academy.student.module.mapper.StudentModuleMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/academy/student_module")
public class StundentModuleController {

    private final StudentModuleService service;
    private final StudentModuleMapper mapper;

    public StundentModuleController(StudentModuleService service, StudentModuleMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentModuleSimpleDto> getById(@PathVariable Long id) {
        StudentModuleEntity entity = service.getById(id);
        return entity != null ? ResponseEntity.ok(mapper.toDto(entity)) : ResponseEntity.notFound().build();
    }

    @GetMapping("/by-course/{courseId}")
    public List<StudentModuleDto> getModuleByCourseId(@PathVariable Long courseId) {
        return service.getAllModuleByCourseId(courseId);
    }
}