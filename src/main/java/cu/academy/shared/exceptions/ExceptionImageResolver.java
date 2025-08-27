package cu.academy.shared.exceptions;

import cu.academy.shared.utils.EndpointResult;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class ExceptionImageResolver {

    @ExceptionHandler(ImageNotFoundException.class)
    public ResponseEntity<EndpointResult> handleImageException(ImageNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new EndpointResult(e.getMessage(), "Image not found"));
    }

}