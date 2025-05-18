package com.ucentral.microservice.exc.controller;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.ucentral.microservice.exc.model.ApiErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class MySqlExceptionHandler {

  private String extractPath(WebRequest request) {
    return request.getDescription(false).replace("uri=", "");
  }

  @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
  public ResponseEntity<?> exceptionHandler(SQLIntegrityConstraintViolationException e, WebRequest request) {

    ApiErrorResponse response = new ApiErrorResponse(
      extractPath(request),
      LocalDateTime.now(),
      Map.of("message", e.getMessage())
    );

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

  }

}
