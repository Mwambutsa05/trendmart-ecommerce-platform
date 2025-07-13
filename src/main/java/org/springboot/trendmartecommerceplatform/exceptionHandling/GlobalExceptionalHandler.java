package org.springboot.trendmartecommerceplatform.exceptionHandling;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionalHandler {
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFound e, HttpServletRequest req) {
        var response = new ErrorResponse();
        response.setMessage(e.getMessage());
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setPath(req.getRequestURI());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest req) {
        List<String> validationErrors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();

        var response = new ErrorResponse();
        response.setMessage("Validation Failed");
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setPath(req.getRequestURI());
        response.setTimestamp(LocalDateTime.now());
        response.setErrors(validationErrors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e, HttpServletRequest req) {
        var response = new ErrorResponse();
        response.setMessage("You do not have permission to access this resource.");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setPath(req.getRequestURI());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAuthorizationDeniedException(AuthorizationDeniedException e, HttpServletRequest req) {
        var response = new ErrorResponse();
        response.setMessage("Authorization denied.");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setPath(req.getRequestURI());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e, HttpServletRequest req) {
        var response = new ErrorResponse();
        response.setMessage("Something went wrong on the server.");
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setPath(req.getRequestURI());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
