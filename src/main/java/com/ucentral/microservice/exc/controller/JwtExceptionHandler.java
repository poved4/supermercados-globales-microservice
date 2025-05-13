package com.ucentral.microservice.exc.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class JwtExceptionHandler {

  @ExceptionHandler(ExpiredJwtException.class)
  public ResponseEntity<?> handleExpiredJwtException(ExpiredJwtException e) {
    return new ResponseEntity<>(
      Map.of("error", "Token expirado"),
      HttpStatus.UNAUTHORIZED
    );
  }

  @ExceptionHandler(UnsupportedJwtException.class)
  public ResponseEntity<?> handleUnsupportedJwtException(UnsupportedJwtException e) {
    return new ResponseEntity<>(
      Map.of("error", "Token no soportado"),
      HttpStatus.UNAUTHORIZED
    );
  }

  @ExceptionHandler(MalformedJwtException.class)
  public ResponseEntity<?> handleMalformedJwtException(MalformedJwtException e) {
    return new ResponseEntity<>(
      Map.of("error", "Token mal formado"),
        HttpStatus.BAD_REQUEST
    );
  }

  @ExceptionHandler(SecurityException.class)
  public ResponseEntity<?> handleSecurityException(SecurityException e) {
    return new ResponseEntity<>(
      Map.of("error", "Error de seguridad en el token"),
        HttpStatus.UNAUTHORIZED
    );
  }

  @ExceptionHandler(SignatureException.class)
  public ResponseEntity<?> handleSignatureException(SignatureException e) {
    return new ResponseEntity<>(
      Map.of("error", "Firma inválida del token"),
        HttpStatus.UNAUTHORIZED
    );
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e) {
    return new ResponseEntity<>(
      Map.of("error", "Token inválido o nulo"),
      HttpStatus.BAD_REQUEST
    );
  }

  @ExceptionHandler(JwtException.class)
  public ResponseEntity<?> handleIllegalArgumentException(JwtException e) {
    return new ResponseEntity<>(
      Map.of("error", "Token inválido o nulo"),
      HttpStatus.BAD_REQUEST
    );
  }

}
