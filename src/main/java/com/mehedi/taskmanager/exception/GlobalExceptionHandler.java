package com.mehedi.taskmanager.exception;

import com.mehedi.taskmanager.model.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle CustomException
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> customCommonException(CustomException exception) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        // Determine the response status based on the exception type
        if (exception.getException().contains("Model")) {
            status = HttpStatus.BAD_REQUEST;
        } else if (exception.getException().contains("Data")) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        // Generate and return a custom ErrorResponse with the appropriate status
        return generateResponse(exception, status);
    }

    @ExceptionHandler(InvalidEntityException.class)
    public ResponseEntity<?> invalidEntity(InvalidEntityException exception) {
        return new ResponseEntity<>(new ErrorResponse(exception.getException(),
                exception.getMessage(), exception.getOperation(), exception.getValidation(),
                HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    // Utility method to generate a custom response
    private ResponseEntity<?> generateResponse(CustomException exception, HttpStatus status) {
        return new ResponseEntity<>(new ErrorResponse(exception.getException(), exception.getMessage(),
                exception.getOperation(), exception.getReason(), status), status);
    }
}