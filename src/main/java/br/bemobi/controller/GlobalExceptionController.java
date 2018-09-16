package br.bemobi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.xml.bind.ValidationException;
import java.net.BindException;
import java.util.HashMap;

@ControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(value = Exception.class)
    private ResponseEntity<?> handleConflictException(Exception e, WebRequest request) {
        HashMap<String, String> error = new HashMap<>();
        error.put("error_code", "002");
        error.put("description", e.getMessage());
        error.put("request", request.toString());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    private ResponseEntity<?> handleValidationException(MethodArgumentNotValidException e) {
        HashMap<String, String> error = new HashMap<>();
        error.put("error_code", "003");
        error.put("description", e.getBindingResult().getFieldError().getDefaultMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
