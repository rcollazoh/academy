package cu.academy.config.classes.image;


import cu.academy.config.classes.image.dto.ClassImageNavigationDto;
import cu.academy.config.course.mapper.ConfigCourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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

    @GetMapping("/class_navegation/{classId}")
    public ResponseEntity<ClassImageNavigationDto> getClassWithNavigation(@PathVariable Long classId) {
        ClassImageNavigationDto image = service.getClassWithNavigation(classId);
        return image != null ? ResponseEntity.ok(image) : ResponseEntity.notFound().build();
    }

    @GetMapping("/image_navegation/{classId}/{id}")
    public ResponseEntity<ClassImageNavigationDto> getImageWithNavigation(@PathVariable Long classId,@PathVariable Long id) {
        ClassImageNavigationDto image = service.getImageWithNavigation(classId,id);
        return image != null ? ResponseEntity.ok(image) : ResponseEntity.notFound().build();
    }
}