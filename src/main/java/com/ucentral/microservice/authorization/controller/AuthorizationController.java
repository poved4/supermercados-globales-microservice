package com.ucentral.microservice.authorization.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ucentral.microservice.authorization.model.dto.SignInRequest;
import com.ucentral.microservice.authorization.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthorizationController {

  private final AuthService authService;

  @GetMapping
  public ResponseEntity<?> validateJWT(@RequestHeader("Authorization") String authHeader) {
    var response = authService.validateToken(authHeader);
    return ResponseEntity.ok(
      Map.of("isValid", response)
    );
  }

  @PostMapping("/signIn")
  public ResponseEntity<?> endPoint(@Valid @RequestBody SignInRequest request) {
    var response = authService.signIn(request);
    return ResponseEntity.ok(
      Map.of("accessToken", response)
    );
  }

  @PostMapping("/logOut")
  public ResponseEntity<?> logOut(@RequestHeader("Authorization") String authHeader) {
    var response = authService.logOut(authHeader);
    return ResponseEntity.ok(
      Map.of("success", response)
    );
  }

}
