package cu.academy.shared.exceptions;

import cu.academy.shared.utils.EndpointResult;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class DBexceptionsResolver {

    Logger logger = LoggerFactory.getLogger(DBexceptionsResolver.class);

    @ExceptionHandler({DataIntegrityViolationException.class, SQLException.class, DataAccessException.class})
    public ResponseEntity<EndpointResult> databaseError(Exception ex) {
        //Todo mejorar manejo de excepciones, cuando es ConstraintViolationException, etc.
        String error = "Error in database operation";
        if (ex.getCause() instanceof ConstraintViolationException && ex.getMessage().contains("viaje.cod_viaje"))
            error = "We hace problem with Application";
        logger.error(error + ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new EndpointResult(null, error));
    }

    @ExceptionHandler({TransactionSystemException.class})
    public ResponseEntity<EndpointResult> handleConstraintViolation(Exception ex) {
        logger.error(ex.getMessage());
        Throwable cause = ((TransactionSystemException) ex).getRootCause();
        String constraintName = "";
        if (cause instanceof ConstraintViolationException) {
            constraintName = ((ConstraintViolationException) cause).getConstraintName();
            // do something here
        }
        assert cause != null;
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new EndpointResult(null, constraintName + cause.getMessage()));
    }
}