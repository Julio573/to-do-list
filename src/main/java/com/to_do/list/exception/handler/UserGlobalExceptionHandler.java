package com.to_do.list.exception.handler;

import com.to_do.list.dto.ApiErrorDTO;
import com.to_do.list.exception.EmailNotFoundException;
import com.to_do.list.exception.IncorrectPasswordMatchException;
import com.to_do.list.exception.InvalidEmailException;
import com.to_do.list.exception.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.UUID;

@ControllerAdvice
public class UserGlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiErrorDTO> handleUserNotFoundException(UserNotFoundException e, HttpServletRequest request) {
        ApiErrorDTO apiErrorDTO = new ApiErrorDTO(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                e.getMessage(),
                request.getMethod(),
                request.getRequestURI(),
                UUID.randomUUID().toString()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiErrorDTO);
    }

    @ExceptionHandler(InvalidEmailException.class)
    ResponseEntity<ApiErrorDTO> handleInvalidEmailException(InvalidEmailException e, HttpServletRequest request) {
        ApiErrorDTO apiErrorDTO = new ApiErrorDTO(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Conflict",
                e.getMessage(),
                request.getMethod(),
                request.getRequestURI(),
                UUID.randomUUID().toString()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiErrorDTO);
    }

    @ExceptionHandler(EmailNotFoundException.class)
    ResponseEntity<ApiErrorDTO> handleEmailNotFoundException(EmailNotFoundException e, HttpServletRequest request) {
        ApiErrorDTO apiErrorDTO = new ApiErrorDTO(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                e.getMessage(),
                request.getMethod(),
                request.getRequestURI(),
                UUID.randomUUID().toString()
                );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiErrorDTO);
    }

    @ExceptionHandler(IncorrectPasswordMatchException.class)
    ResponseEntity<ApiErrorDTO> handleIncorrectPasswordMatchException(IncorrectPasswordMatchException e, HttpServletRequest request) {
        ApiErrorDTO apiErrorDTO = new ApiErrorDTO(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                e.getMessage(),
                request.getMethod(),
                request.getRequestURI(),
                UUID.randomUUID().toString()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiErrorDTO);
    }
}
