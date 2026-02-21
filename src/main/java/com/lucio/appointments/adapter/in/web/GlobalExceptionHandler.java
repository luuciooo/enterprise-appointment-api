package com.lucio.appointments.adapter.in.web;

import com.lucio.appointments.adapter.in.web.error.ErrorResponse;
import com.lucio.appointments.domain.exception.BusinessException;
import com.lucio.appointments.domain.exception.EmailAlreadyExistsException;
import com.lucio.appointments.domain.exception.InvalidCredentialsException;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusiness(
            BusinessException ex,
            HttpServletRequest request
    ) {
        return buildResponse(
                ex.getMessage(),
                ex.getErrorCode().name(),
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentials(
            InvalidCredentialsException ex,
            HttpServletRequest request
    ) {
        return buildResponse(
                ex.getMessage(),
                "INVALID_CREDENTIALS",
                HttpStatus.UNAUTHORIZED,
                request
        );
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExists(
            EmailAlreadyExistsException ex,
            HttpServletRequest request
    ) {
        return buildResponse(
                ex.getMessage(),
                "EMAIL_ALREADY_EXISTS",
                HttpStatus.CONFLICT,
                request
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(
            Exception ex,
            HttpServletRequest request
    ) {
        return buildResponse(
                "Unexpected error",
                "INTERNAL_ERROR",
                HttpStatus.INTERNAL_SERVER_ERROR,
                request
        );
    }

    private ResponseEntity<ErrorResponse> buildResponse(
            String message,
            String code,
            HttpStatus status,
            HttpServletRequest request
    ) {

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                code,
                message,
                request.getRequestURI(),
                UUID.randomUUID().toString().substring(0, 12),
                request.getHeader("X-Tenant-Id")
        );

        return ResponseEntity.status(status).body(error);
    }
}
