package com.ucentral.microservice.exc.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.ucentral.microservice.exc.model.ApiErrorResponse;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private String extractPath(WebRequest request) {
    return request.getDescription(false).replace("uri=", "");
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> exceptionHandler(MethodArgumentNotValidException e, WebRequest request) {

    Map<String, String> errors = new HashMap<>();

    e.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });

    ApiErrorResponse response = new ApiErrorResponse(
      extractPath(request),
      LocalDateTime.now(),
      errors
    );

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<?> exceptionHandler(ConstraintViolationException e, WebRequest request) {

    ApiErrorResponse response = new ApiErrorResponse(
      extractPath(request),
      LocalDateTime.now(),
      e.getMessage()
    );

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

  }

}