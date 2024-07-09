package com.store.grocery.fresh_express.handler;

import com.store.grocery.fresh_express.custom_exception.ApiException;
import com.store.grocery.fresh_express.custom_exception.ResourceNotFoundException;
import com.store.grocery.fresh_express.custom_exception.UserAlreadyExistsException;
import com.store.grocery.fresh_express.utils.ExceptionResponse;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({UsernameNotFoundException.class,
            ResourceNotFoundException.class,
            NoSuchElementException.class
    })
    public ResponseEntity<ExceptionResponse> handleNotFoundExceptions(Exception exception, WebRequest request) {
        ExceptionResponse response = buildExceptionResponse(exception, HttpStatus.NOT_FOUND, request);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler({
            ApiException.class,
            IllegalStateException.class,
            DataIntegrityViolationException.class,
            StringIndexOutOfBoundsException.class,
            MethodArgumentTypeMismatchException.class,
            PropertyReferenceException.class,
            ConstraintViolationException.class,
            MissingServletRequestPartException.class,
            IllegalArgumentException.class,
            UserAlreadyExistsException.class
    })
    public ResponseEntity<ExceptionResponse> handleBadRequestExceptions(Exception exception, WebRequest request) {
        ExceptionResponse response = buildExceptionResponse(exception, HttpStatus.BAD_REQUEST, request);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<ObjectError> errors = exception.getBindingResult().getAllErrors();
        Map<String, Object> response = new HashMap<>();
        errors.forEach(object -> {
            String message = object.getDefaultMessage();
            String field = ((FieldError) object).getField();
            response.put(field, message);
        });
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(response);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ExceptionResponse> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException exception, WebRequest request) {
        ExceptionResponse response = buildExceptionResponse(exception, HttpStatus.METHOD_NOT_ALLOWED, request);
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(response);
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<ExceptionResponse> handleMultipartException(MultipartException exception, WebRequest request) {
        ExceptionResponse response = buildExceptionResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR, request);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }


    @ExceptionHandler({AccessDeniedException.class, ExpiredJwtException.class})
    public ResponseEntity<ExceptionResponse> handleAccessDeniedException(AccessDeniedException exception, WebRequest request) {
        ExceptionResponse response = buildExceptionResponse(exception, HttpStatus.FORBIDDEN, request);
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(response);
    }


    private ExceptionResponse buildExceptionResponse(Exception exception, HttpStatus status, WebRequest request) {
        return ExceptionResponse.builder()
                .statusCode(status.value())
                .message(exception.getMessage())
                .details(request.getDescription(false))
                .build();
    }
}
