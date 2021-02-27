package com.brtvsk.lab4.errorHandling;

import com.brtvsk.lab4.validation.BookValidationException;
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

}
