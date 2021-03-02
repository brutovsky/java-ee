package com.brtvsk.lab4.errorHandling;

import com.brtvsk.lab4.validation.BookValidationException;
import org.hibernate.engine.jdbc.env.spi.SQLStateType;
import org.postgresql.util.PSQLException;
import org.postgresql.util.PSQLState;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BookValidationException.class)
    protected ResponseEntity<Object> handleBookValidationException(
            BookValidationException bve, WebRequest request
    ) {
        ApiError apiError =
                new ApiError(HttpStatus.BAD_REQUEST, bve.getLocalizedMessage(), bve.toString());
        return handleExceptionInternal(
                bve, apiError, new HttpHeaders(), apiError.getStatus(), request);
    }

    @ExceptionHandler(PSQLException.class)
    protected ResponseEntity<Object> handlePSQLException(
            PSQLException psqlException, WebRequest request
    ) {
        ApiError apiError;
        if (psqlException.getSQLState().equals(PSQLState.UNIQUE_VIOLATION.getState())) {
            apiError =
                    new ApiError(HttpStatus.BAD_REQUEST, "Книга з таким ISBN вже існує :/", psqlException.getMessage());
        } else {
            apiError =
                    new ApiError(HttpStatus.BAD_REQUEST, psqlException.getLocalizedMessage(), psqlException.toString());
        }

        return handleExceptionInternal(
                psqlException, apiError, new HttpHeaders(), apiError.getStatus(), request);
    }

}
