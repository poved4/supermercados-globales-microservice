package com.ucentral.microservice.exc.controller;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.ucentral.microservice.exc.model.ApiErrorResponse;
import com.ucentral.microservice.exc.model.BadRequestException;
import com.ucentral.microservice.exc.model.NoContentException;
import com.ucentral.microservice.exc.model.UnauthorizedException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

  private String extractPath(WebRequest request) {
    return request.getDescription(false).replace("uri=", "");
  }

  @ExceptionHandler(NoContentException.class)
  public ResponseEntity<?> exceptionHandler(NoContentException e) {
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @ExceptionHandler({
    BadRequestException.class,
    IllegalArgumentException.class
  })
  public ResponseEntity<?> exceptionHandler(RuntimeException e, WebRequest request) {

    ApiErrorResponse response = new ApiErrorResponse(
      extractPath(request),
      LocalDateTime.now(),
      Map.of("message", e.getMessage())
    );

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

  }

  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<?> exceptionHandler(UnauthorizedException e, WebRequest request) {

    ApiErrorResponse response = new ApiErrorResponse(
      extractPath(request),
      LocalDateTime.now(),
      Map.of("message", e.getMessage())
    );

    return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);

  }

}
