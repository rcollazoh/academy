package cu.academy.student.exam;

import cu.academy.shared.enum_types.EnumExamStatus;
import cu.academy.shared.exceptions.ArgumentException;
import cu.academy.student.exam.dto.StudentExamDto;
import cu.academy.student.exam.mapper.StudentExamMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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