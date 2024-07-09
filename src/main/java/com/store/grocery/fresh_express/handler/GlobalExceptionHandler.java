package com.store.grocery.fresh_express.handler;

import com.store.grocery.fresh_express.utils.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionResponse> userNameNotFoundException(UsernameNotFoundException exception, WebRequest request) {
        ExceptionResponse response = ExceptionResponse.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(exception.getMessage())
                .details(request.getDescription(false))
                .build();
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND.value())
                .body(response);
    }
}
