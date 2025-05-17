package com.ucentral.microservice.exc.controller;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.ucentral.microservice.exc.model.ApiErrorResponse;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class JwtExceptionHandler {

  private String extractPath(WebRequest request) {
    return request.getDescription(false).replace("uri=", "");
  }

  @ExceptionHandler(ExpiredJwtException.class)
  public ResponseEntity<?> exceptionHandler(ExpiredJwtException e, WebRequest request) {

    ApiErrorResponse response = new ApiErrorResponse(
      extractPath(request),
      LocalDateTime.now(),
      Map.of("message", e.getMessage())
    );

    return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);

  }

  @ExceptionHandler({
    JwtException.class,
    SecurityException.class,
    SignatureException.class,
    MalformedJwtException.class,
    UnsupportedJwtException.class
  })
  public ResponseEntity<?> exceptionHandler(RuntimeException e, WebRequest request) {

    ApiErrorResponse response = new ApiErrorResponse(
      extractPath(request),
      LocalDateTime.now(),
      Map.of("message", e.getMessage())
    );

    return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);

  }

}
