package com.dacchub.backend.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dacchub.backend.auth.dto.UserLoginDto;
import com.dacchub.backend.auth.dto.UserRegisterDto;
import com.dacchub.backend.auth.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  private AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(@Valid @RequestBody UserLoginDto user) {
    try {
      return ResponseEntity.ok(authService.login(user));
    } catch (AuthenticationException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
  }

  @PostMapping("/signup")
  public ResponseEntity<String> register(@Valid @RequestBody UserRegisterDto user) {

    try {
      return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(user));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

  }

  @PostMapping
  public ResponseEntity<String> refresh(HttpServletRequest request, HttpServletResponse response) {
    try {
      return ResponseEntity.ok(authService.refreshToken(request, response));
    } catch (AuthenticationException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
  }
}
