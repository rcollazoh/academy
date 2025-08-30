package cu.academy.images;

import cu.academy.shared.utils.EndpointResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "academy/imagenes")
public class ImagesController {

    private final FilesStorageService storageService;

    @Autowired
    public ImagesController(FilesStorageService service) {
        this.storageService = service;
    }

    @GetMapping(produces = "multipart/form-data")
    public ResponseEntity<byte[]> getImage(@RequestParam("type") String type, @RequestParam("filename") String filename) {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(storageService.getFile(type, filename));
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<EndpointResult> saveImage(@RequestParam("filePathname") String imagePath,
                                                    @RequestParam("image") MultipartFile file) {
        String result = storageService.save(file, imagePath);
        return ResponseEntity.ok(new EndpointResult(result, null));
    }

    @DeleteMapping
    public ResponseEntity<EndpointResult> deleteImage(@RequestParam("type") String type, @RequestParam("filename") String filename) {
        storageService.delete(type, filename);
        return ResponseEntity.ok(new EndpointResult("Imagen borrada", null));
    }

}
