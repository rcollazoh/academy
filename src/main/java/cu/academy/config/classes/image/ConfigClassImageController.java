package cu.academy.config.classes.image;


import cu.academy.config.classes.image.dto.ClassImageNavigationDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/academy/config_class_image")
public class ConfigClassImageController {

    private final ConfigClassImageService service;

    public ConfigClassImageController(ConfigClassImageService service) {
        this.service = service;
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