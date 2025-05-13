package com.ucentral.microservice.authorization.service;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.ucentral.microservice.authorization.model.dto.SignInRequest;
import com.ucentral.microservice.authorization.model.entity.Account;
import com.ucentral.microservice.authorization.model.entity.Session;
import com.ucentral.microservice.authorization.repository.AccountRepository;
import com.ucentral.microservice.authorization.repository.SessionRepository;
import com.ucentral.microservice.exc.model.UnauthorizedException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final JwtService jwtService;
  private final SessionRepository sessionRepository;
  private final AccountRepository accountRepository;

  private String getAccessToken(String authorizationHeader) {

    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
      throw new IllegalArgumentException("Invalid Bearer Token");
    }

    final String accessToken = authorizationHeader.substring(7).trim();

    if (!jwtService.isValidFormat(accessToken)) {
      throw new IllegalArgumentException("Invalid Bearer Token");
    }

    if (jwtService.isTokenExpired(accessToken)) {
      throw new UnauthorizedException("Invalid or expired token");
    }

    return accessToken.trim();

  }

  private Session getCurrentSession(String accessToken) {
    return sessionRepository
        .findByAccessToken(accessToken)
        .orElse(null);
  }

  public String signIn(SignInRequest request) {

    Account account = accountRepository
        .findByEmail(request.getEmail())
        .orElseThrow(
            () -> new UnauthorizedException("Invalid credentials"));

    if (!request.getPassword().equals(account.getPassword())) {
      throw new UnauthorizedException("Invalid credentials");
    }

    String accessToken = jwtService.generateToken(account);

    Session session = Session.builder()
        .accountId(account.getId())
        .loginStatus((byte) 1)
        .accessToken(accessToken)
        .ipAddress(request.getIpAddress())
        .deviceUsed(request.getDeviceUsed())
        .loginTime(LocalDateTime.now())
        .build();

    sessionRepository.save(session);

    return accessToken;

  }

  public Boolean logOut(String authorizationHeader) {

    final String accessToken = getAccessToken(authorizationHeader);
    Session currentSession = getCurrentSession(accessToken);

    if (currentSession == null) {
      throw new UnauthorizedException("Invalid or expired token");
    }

    if (currentSession.getLoginStatus() == 0) {
      throw new UnauthorizedException("session currently closed");
    }

    currentSession.setLoginStatus((byte) 0);
    currentSession.setLogoutTime(LocalDateTime.now());
    sessionRepository.save(currentSession);

    // "Logged out successfully"
    return Boolean.TRUE;

  }

  public Boolean validateToken(String authorizationHeader) {

    final String accessToken = getAccessToken(authorizationHeader);

    Session currentSession = getCurrentSession(accessToken);
    if (Objects.isNull(currentSession)) {
      throw new UnauthorizedException("session does not exist");
    }

    if (currentSession.getLoginStatus() == 0) {
      throw new UnauthorizedException("session currently closed");
    }

    final String userEmail = jwtService.extractUsername(accessToken);
    var account = accountRepository.findByEmail(userEmail);
    if (!account.isPresent()) {
      throw new IllegalArgumentException("Invalid Bearer Token");
    }

    if (account.get().getId() != currentSession.getAccountId()) {
      throw new UnauthorizedException("token does not match the account");
    }

    return Boolean.TRUE;

  }

}