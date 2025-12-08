package com.to_do.list.exception.handler;

import com.to_do.list.dto.ApiErrorDTO;
import com.to_do.list.exception.InvalidEmailException;
import com.to_do.list.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class UserGlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiErrorDTO> handleUserNotFoundException(UserNotFoundException e) {
        ApiErrorDTO apiErrorDTO = new ApiErrorDTO(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiErrorDTO);
    }

    @ExceptionHandler(InvalidEmailException.class)
    ResponseEntity<ApiErrorDTO> handleInvalidEmailException(InvalidEmailException e) {
        ApiErrorDTO apiErrorDTO = new ApiErrorDTO(
                HttpStatus.CONFLICT.value(),
                e.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiErrorDTO);
    }
}
