package cu.academy.shared.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Image not found", value = HttpStatus.NOT_FOUND)
public class ImageNotFoundException extends RuntimeException {
}
