package com.dacchub.backend.auth.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtUtil {

  @Value("${jwt.secret}")
  private String jwtSecret;

  @Value("${jwt.expiration}")
  private int jwtExpirationMs;

  @Value("${jwt.refresh-expiration}")
  private long refreshExpiration;

  private SecretKey key;

  private static final String BEARER = "Bearer ";

  public JwtUtil() {

  }

  @PostConstruct
  public void init() {
    this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
  }

  public String generateToken(String username) {
    var now = new Date();

    return Jwts.builder()
        .claim("type", "ACCESS")
        .subject(username)
        .issuedAt(now)
        .expiration(new Date(now.getTime() + jwtExpirationMs))
        .signWith(key)
        .compact();
  }

  public String generateRefreshToken(String username, UUID uuid) {
    var now = new Date();

    return Jwts.builder()
        .claim("type", "REFRESH")
        .subject(username)
        .id(uuid.toString())
        .issuedAt(now)
        .expiration(new Date(now.getTime() + refreshExpiration))
        .signWith(key)
        .compact();
  }

  public String getUserFromToken(String token) {
    return Jwts.parser().verifyWith(key).build()
        .parseSignedClaims(token)
        .getPayload()
        .getSubject();
  }

  public UUID getTokenIdFromToken(String token) {
    String id = Jwts.parser().verifyWith(key).build()
        .parseSignedClaims(token)
        .getPayload()
        .getId();
    return UUID.fromString(id);
  }

  public boolean validateJwtToken(String token) {
    try {
      Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
      return true;
    } catch (Exception e) {
      log.error("Jwt Validation Error: {}", e.getMessage());
    }

    return false;
  }

  public String parseJwt(HttpServletRequest request) {
    String headerAuth = request.getHeader("Authorization");

    if (headerAuth != null && headerAuth.startsWith(BEARER)) {
      return headerAuth.substring(BEARER.length());
    }

    return null;
  }

  public String getTokenType(String token) {
    return Jwts.parser().verifyWith(key).build()
        .parseSignedClaims(token)
        .getPayload()
        .get("type", String.class);
  }

}
