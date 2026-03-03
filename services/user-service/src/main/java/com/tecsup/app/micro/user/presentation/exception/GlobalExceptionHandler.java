package com.tecsup.app.micro.user.presentation.exception;

import com.tecsup.app.micro.user.domain.exception.AddressNotFoundException;
import com.tecsup.app.micro.user.domain.exception.UserNotFoundException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    record ErrorResponse(String code, String message, List<Map<String, String>> details) {
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> validation(MethodArgumentNotValidException ex) {
        var details = ex.getBindingResult().getFieldErrors().stream()
                .map(f -> Map.of("field", f.getField(), "issue", f.getDefaultMessage()))
                .toList();

        return ResponseEntity.badRequest().body(new ErrorResponse("VALIDATION_ERROR", "Request inválido", details));
    }

    @ExceptionHandler({ UserNotFoundException.class, AddressNotFoundException.class })
    public ResponseEntity<ErrorResponse> notFound(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("NOT_FOUND", ex.getMessage(), List.of()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> generic(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("INTERNAL_ERROR", "Error interno", List.of()));
    }
}