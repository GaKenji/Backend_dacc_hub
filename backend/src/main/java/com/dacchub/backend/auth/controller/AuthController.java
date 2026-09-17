package com.dacchub.backend.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dacchub.backend.auth.dto.AuthResponseDto;
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
  public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody UserLoginDto user) {
    try {
      return ResponseEntity.ok(authService.login(user));
    } catch (AuthenticationException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
  }

  @PostMapping("/signup")
  public ResponseEntity<AuthResponseDto> register(@Valid @RequestBody UserRegisterDto user) {

    try {
      return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(user));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    }

  }

  @PostMapping("/refresh")
  public ResponseEntity<String> refresh(HttpServletRequest request, HttpServletResponse response) {
    try {
      return ResponseEntity.ok(authService.refreshToken(request, response));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
  }

  @PostMapping("/logout")
  public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
    try {
      return ResponseEntity.ok(authService.logout(request, response));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
  }

  @PostMapping("/logout-all")
  public ResponseEntity<String> logoutAll(HttpServletRequest request, HttpServletResponse response) {
    try {
      return ResponseEntity.ok(authService.logoutAll(request, response));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
  }
}
