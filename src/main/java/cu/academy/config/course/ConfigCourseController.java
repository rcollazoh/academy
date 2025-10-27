package cu.academy.config.course;


import cu.academy.config.course.dto.ConfigCourseDto;
import cu.academy.config.course.mapper.ConfigCourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/academy/course")
public class ConfigCourseController {

    private final ConfigCourseService service;
    private final ConfigCourseMapper mapper;

    public ConfigCourseController(ConfigCourseService service, ConfigCourseMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<ConfigCourseDto> getAll() {
        return service.getAllSort()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConfigCourseDto> getById(@PathVariable Long id) {
        ConfigCourseEntity person = service.getById(id);
        return person != null ? ResponseEntity.ok(mapper.toDto(person)) : ResponseEntity.notFound().build();
    }

    @GetMapping("/by-area/{areaId}/by-practice/{practiceId}")
    public ResponseEntity<ConfigCourseDto> getCourseByAreaAndPractice(
            @PathVariable Long areaId,
            @PathVariable Long practiceId) {
        ConfigCourseEntity courseResponse = service.getCourseByAreaAndPractice(areaId, practiceId);
        return courseResponse != null ? ResponseEntity.ok(mapper.toDto(courseResponse)) : ResponseEntity.notFound().build();
    }
}