package cu.academy.email;


import cu.academy.shared.exceptions.ArgumentException;
import cu.academy.shared.utils.EndpointResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "academy/email")
public class EmailController {
    private final EmailService service;

    public EmailController(EmailService service) {
        this.service = service;
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<EndpointResult> send(

            @RequestParam("correoDestino") String correoDestino,
            @RequestParam("asunto") String asunto,
            @RequestParam("contenido") String contenido,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "ccEmails", required = false) String ccEmails) throws ArgumentException {
        service.sendMessageAndAttachmentWithFile(correoDestino, asunto, contenido, file, ccEmails);
        return ResponseEntity.ok(new EndpointResult("ok", null));
    }

    @PostMapping("/feedback")
    public ResponseEntity<EndpointResult> sendFeedback(
            @RequestParam("personName") String personName,
            @RequestParam("message") String message) throws ArgumentException {
        service.sendFeedback(personName, message);
        return ResponseEntity.ok(new EndpointResult("ok", null));
    }
}
