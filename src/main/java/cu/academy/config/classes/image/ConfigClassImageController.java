package cu.academy.config.classes.image;

import cu.academy.config.classes.ConfigClassEntity;
import cu.academy.config.classes.ConfigClassService;
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

    @GetMapping("/class_navegation/{configClassId}/{currentImageId}")
    public ResponseEntity<ClassImageNavigationDto> getClassWithNavigation(@PathVariable Long currentImageId, @PathVariable Long configClassId) {
        ClassImageNavigationDto image = service.getClassWithNavigation(currentImageId,configClassId);
        return image != null ? ResponseEntity.ok(image) : ResponseEntity.notFound().build();
    }

    @GetMapping("/image_navegation/{configClassId}/{id}")
    public ResponseEntity<ClassImageNavigationDto> getImageWithNavigation(@PathVariable Long configClassId,@PathVariable Long id) {
        ClassImageNavigationDto image = service.getImageWithNavigation(configClassId,id);
        return image != null ? ResponseEntity.ok(image) : ResponseEntity.notFound().build();
    }
}