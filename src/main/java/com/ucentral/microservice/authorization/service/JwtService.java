package com.ucentral.microservice.authorization.service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ucentral.microservice.authorization.model.entity.Account;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

  @Value("${application.security.jwt.secret-key}")
  private String secretKey;

  @Value("${application.security.jwt.expiration}")
  private Long jwtExpiration;

  private Key getSigningKey() {
    return Keys.hmacShaKeyFor(secretKey.getBytes());
  }

  public String generateToken(Account account) {

    final Date now = new Date();
    final Date expiry = new Date(now.getTime() + jwtExpiration);

    return Jwts.builder()
        .setId(account.getId().toString().trim())
        .setSubject(account.getEmail().trim())
        .setIssuedAt(now)
        .setExpiration(expiry)
        .addClaims(Map.of("name", account.getName().trim()))
        .signWith(getSigningKey(), SignatureAlgorithm.HS256)
        .compact();

  }

  private Claims extractAllClaims(String token) {

    if (!isValidFormat(token)) {
      throw new IllegalArgumentException("El token JWT tiene un formato inválido.");
    }

    return Jwts.parserBuilder()
      .setSigningKey(getSigningKey())
      .build()
      .parseClaimsJws(token)
      .getBody();

  }

  public String extractUsername(String token) {
    return extractAllClaims(token).getSubject();
  }

  public boolean isTokenExpired(String token) {
    return extractAllClaims(token)
      .getExpiration()
      .before(new Date());
  }

  public boolean isValidFormat(String token) {
    return Pattern
      .compile("^[A-Za-z0-9_-]+\\.[A-Za-z0-9_-]+\\.[A-Za-z0-9_-]+$")
      .matcher(token)
      .matches();
  }

}
