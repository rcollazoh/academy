package cu.academy.student.classes;

import cu.academy.shared.exceptions.ArgumentException;
import cu.academy.student.classes.dto.StudentClassDto;
import cu.academy.student.classes.mapper.StudentClassMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/academy/student_class")
public class StundentClassController {

    private final StudentClassService service;
    private final StudentClassMapper mapper;

    public StundentClassController(StudentClassService service, StudentClassMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentClassDto> getById(@PathVariable Long id) {
        StudentClassEntity entity = service.getById(id);
        return entity != null ? ResponseEntity.ok(mapper.toDto(entity)) : ResponseEntity.notFound().build();
    }

    @PutMapping(path = "/{id}/{status}/{currentImageId}")
    public void updateStatus(@PathVariable("id") Long id,
                       @PathVariable boolean status,
                             @PathVariable("currentImageId") Long currentImageId ) throws ArgumentException {
        service.updateViewedAndCurrentImageId(id, status, currentImageId);
    }
}