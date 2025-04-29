package com.ucentral.microservice.exc.controller;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class MySqlExceptionHandler {

  @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
  public ResponseEntity<?> handleException(SQLIntegrityConstraintViolationException e) {
    return new ResponseEntity<>(
      Map.of("error", e.getMessage()),
      HttpStatus.BAD_REQUEST
    );
  }

}
