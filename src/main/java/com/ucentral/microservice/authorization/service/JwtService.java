package com.ucentral.microservice.authorization.service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ucentral.microservice.authorization.model.entity.Account;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

  private static final String BEARER_PREFIX = "Bearer ";

  private static final Pattern JWT_PATTERN = Pattern.compile("^[A-Za-z0-9_-]+\\.[A-Za-z0-9_-]+\\.[A-Za-z0-9_-]+$");

  @Value("${application.security.jwt.secret-key}")
  private String secretKey;

  @Value("${application.security.jwt.expiration}")
  private Long jwtExpirationMillis;

  public String getBearerKey() {
    return BEARER_PREFIX;
  }

  private Key getSigningKey() {
    return Keys.hmacShaKeyFor(secretKey.getBytes());
  }

  private Claims extractAllClaims(String jwt) {
    return Jwts
        .parserBuilder()
        .setSigningKey(getSigningKey())
        .build()
        .parseClaimsJws(jwt)
        .getBody();
  }

  public String extractUsername(String jwt) {
    return extractAllClaims(jwt).getSubject();
  }

  public Boolean isTokenExpired(String jwt) {
    try {
      return extractAllClaims(jwt)
          .getExpiration()
          .before(new Date());
    } catch (JwtException | IllegalArgumentException e) {
      return true;
    }
  }

  private Boolean isValidTokenFormat(String jwt) {
    return JWT_PATTERN.matcher(jwt).matches();
  }

  public Boolean isValidToken(String jwt) {
    return isValidTokenFormat(jwt)
        && !isTokenExpired(jwt);
  }

  public String generateToken(Account account) {

    final Date now = new Date();
    final Date expiry = new Date(now.getTime() + jwtExpirationMillis);

    Map<String, Object> claims = Map.of(
        "id", String.valueOf(account.getId())
    );

    return Jwts.builder()
        .setId(String.valueOf(account.getId()))
        .setSubject(account.getEmail())
        .setIssuedAt(now)
        .setExpiration(expiry)
        .addClaims(claims)
        .signWith(getSigningKey(), SignatureAlgorithm.HS256)
        .compact();
  }

}
