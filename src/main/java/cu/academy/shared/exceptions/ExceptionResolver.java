package cu.academy.shared.exceptions;

import cu.academy.shared.utils.EndpointResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestControllerAdvice
class ExceptionResolver {

    Logger logger = LoggerFactory.getLogger(ExceptionResolver.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<EndpointResult> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
//        Map<String, String> errors = new HashMap<>();
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
//            String fieldName = "Error";
//            if (error instanceof FieldError)
//                fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
            errors.add(errorMessage);
        });
//        String result = errors.toString();
        String result = String.join(System.lineSeparator(), errors);
        if (result.contains("{"))
            result = result.substring(1, result.length() - 1);
        logger.warn(result);
        return ResponseEntity.badRequest().body(new EndpointResult<>(null, result));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<EndpointResult> handleValidationExceptions(
            MissingServletRequestParameterException ex) {
        String error = "The parameter " + ex.getParameterName() + " of type " + ex.getParameterType() + " is requested.";
        logger.warn(error);
        return ResponseEntity.badRequest().body(new EndpointResult<>(null, error));
    }

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<EndpointResult> handleResourceAccessExceptions(ResourceAccessException ex) {
        String message = ex.getMessage();
        assert message != null;
        String error = "Error accessing the resource: " + message.split("\"")[1];
        logger.error(error);
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new EndpointResult<>(null, error));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<EndpointResult> handleArgumentException(Exception e) {
        logger.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new EndpointResult<>(null, e.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<EndpointResult> handleArgumentException(HttpMessageNotReadableException e) {
        logger.error(e.getMessage(), e);
        String[] strings = Objects.requireNonNull(e.getMessage()).split("\"");
        List<String> stringsArray = Arrays.asList(strings);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new EndpointResult<>(null,
                "Error input parameter: " + stringsArray.get(stringsArray.size() - 2)));
    }

}