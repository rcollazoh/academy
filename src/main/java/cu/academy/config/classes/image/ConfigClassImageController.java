package cu.academy.config.classes.image;


import cu.academy.config.course.ConfigCourseEntity;
import cu.academy.config.course.ConfigCourseService;
import cu.academy.config.course.dto.ConfigCourseDto;
import cu.academy.config.course.mapper.ConfigCourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/academy/config_class_image")
public class ConfigClassImageController {

    private final ConfigClassImageService service;
    private final ConfigCourseMapper mapper;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public ConfigClassImageController(ConfigClassImageService service, ConfigCourseMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }
//
//    @GetMapping
//    public List<ConfigCourseDto> getAll() {
//        return service.getAllSort()
//                .stream()
//                .map(mapper::toDto)
//                .collect(Collectors.toList());
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<ConfigCourseDto> getById(@PathVariable Long id) {
//        ConfigCourseEntity person = service.getById(id);
//        return person != null ? ResponseEntity.ok(mapper.toDto(person)) : ResponseEntity.notFound().build();
//    }
//
//    @GetMapping("/by-area/{areaId}/by-practice/{practiceId}")
//    public ResponseEntity<ConfigCourseDto> getCourseByAreaAndPractice(
//            @PathVariable Long areaId,
//            @PathVariable Long practiceId) {
//        ConfigCourseEntity courseResponse = service.getCourseByAreaAndPractice(areaId, practiceId);
//        return courseResponse != null ? ResponseEntity.ok(mapper.toDto(courseResponse)) : ResponseEntity.notFound().build();
//    }
}