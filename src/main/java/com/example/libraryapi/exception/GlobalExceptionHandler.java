package com.example.libraryapi.exception;

import com.example.libraryapi.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleBookNotFoundException(
            BookNotFoundException exception,
            HttpServletRequest request) {
        return new ErrorResponse(
                LocalDateTime.now(),
                404,
                "Not Found",
                exception.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {
        String message = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Validation error");

        return new ErrorResponse(
                LocalDateTime.now(),
                400,
                "Bad Request",
                message,
                request.getRequestURI()
        );
    }

    @ExceptionHandler(BookAlreadyOnLoanException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBookAlreadyLoaned(
            BookAlreadyOnLoanException exception,
            HttpServletRequest request) {
        return new ErrorResponse(
                LocalDateTime.now(),
                400,
                "Bad Request",
                exception.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntime(
            RuntimeException exception,
            HttpServletRequest request) {
        return ResponseEntity.status(500).body(
            new ErrorResponse(
                    LocalDateTime.now(),
                    500,
                    "Internal Server Error",
                    exception.getMessage(),
                    request.getRequestURI()
            )
        );
    }
}