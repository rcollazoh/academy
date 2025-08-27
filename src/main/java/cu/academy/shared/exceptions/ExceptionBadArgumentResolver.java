package cu.academy.shared.exceptions;

import cu.academy.shared.utils.EndpointResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.management.OperationsException;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class ExceptionBadArgumentResolver {

    Logger logger = LoggerFactory.getLogger(ExceptionBadArgumentResolver.class);

    @ExceptionHandler({ArgumentException.class})
    public ResponseEntity<EndpointResult> handleArgumentException(ArgumentException e) {
        logger.warn(e.getMessage());
        return ResponseEntity.badRequest().body(new EndpointResult(null, e.getMsg()));
    }

    @ExceptionHandler({BadCredentialsException.class, UsernameNotFoundException.class, IllegalStateException.class})
    public ResponseEntity<EndpointResult> handleSecurityExceptions(Exception e) {
        logger.warn(e.getMessage());
        return ResponseEntity.badRequest().body(new EndpointResult(null, e.getMessage()));
    }

    @ExceptionHandler({OperationsException.class})
    public ResponseEntity<EndpointResult> handleOperationsException(OperationsException e) {
        logger.warn(e.getMessage());
        return ResponseEntity.badRequest().body(new EndpointResult(null, e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<EndpointResult> handleArgumentException(MethodArgumentTypeMismatchException e) {
        logger.warn(e.getMessage());
        return ResponseEntity.badRequest().body(new EndpointResult(null, "The value of the parameter " + e.getName() + " no es correcto."));
    }

}