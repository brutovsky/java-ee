package com.brtvsk.lab4.errorHandling;

import com.brtvsk.lab4.validation.BookValidationException;
import org.hibernate.engine.jdbc.env.spi.SQLStateType;
import org.postgresql.util.PSQLException;
import org.postgresql.util.PSQLState;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PSQLException.class)
    protected ResponseEntity<Object> handlePSQLException(
            PSQLException psqlException
    ) {
        ApiError apiError;
        if (psqlException.getSQLState().equals(PSQLState.UNIQUE_VIOLATION.getState())) {
            apiError =
                    new ApiError(HttpStatus.BAD_REQUEST, "Unique constraints violation", psqlException.getMessage());
        } else {
            apiError =
                    new ApiError(HttpStatus.BAD_REQUEST, psqlException.getLocalizedMessage(), psqlException.toString());
        }
        List<String> errors = List.of(apiError.getMessage());
        return ResponseEntity.badRequest().body(errors);
    }

}
