package com.lucio.appointments.adapter.in.web;

import com.lucio.appointments.domain.exception.EmailAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final ZoneId APP_ZONE = ZoneId.of("America/Argentina/Buenos_Aires");

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<?> handleEmailAlreadyExists(EmailAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                Map.of(
                        "timestamp", OffsetDateTime.now(APP_ZONE),
                        "status", 409,
                        "error", "Conflict",
                        "message", ex.getMessage()
                )
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                Map.of(
                        "timestamp", OffsetDateTime.now(APP_ZONE),
                        "status", 500,
                        "error", "Internal Server Error",
                        "message", "Unexpected error occurred"
                )
        );
    }
}
