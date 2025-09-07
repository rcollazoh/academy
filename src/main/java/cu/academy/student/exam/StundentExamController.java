package cu.academy.student.exam;

import cu.academy.shared.enum_types.EnumExamStatus;
import cu.academy.shared.enum_types.EnumModuleStatus;
import cu.academy.shared.exceptions.ArgumentException;
import cu.academy.student.exam.dto.StudentExamDto;
import cu.academy.student.exam.mapper.StudentExamMapper;
import cu.academy.student.module.StudentModuleEntity;
import cu.academy.student.module.StudentModuleService;
import cu.academy.student.module.dto.StudentModuleDto;
import cu.academy.student.module.dto.StudentModuleSimpleDto;
import cu.academy.student.module.mapper.StudentModuleMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/academy/student_exam")
public class StundentExamController {

    private final StudentExamService service;
    private final StudentExamMapper mapper;

    public StundentExamController(StudentExamService service, StudentExamMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentExamDto> getById(@PathVariable Long id) {
        StudentExamEntity entity = service.getById(id);
        return entity != null ? ResponseEntity.ok(mapper.toDto(entity)) : ResponseEntity.notFound().build();
    }

    @PutMapping(path = "/{id}/{status}")
    public void updateStatus(@PathVariable("id") Long id,
                       @PathVariable EnumExamStatus status) throws ArgumentException {
        service.updateStatusAndModule(id, status);
    }
}