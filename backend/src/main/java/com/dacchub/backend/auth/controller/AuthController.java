package com.dacchub.backend.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dacchub.backend.auth.dto.UserLoginDto;
import com.dacchub.backend.auth.dto.UserRegisterDto;
import com.dacchub.backend.auth.service.AuthService;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

  private AuthService authService;

  @PostMapping("/login")
  private ResponseEntity<String> login(@RequestBody UserLoginDto user) {
    try {
      return ResponseEntity.ok(authService.login(user));
    } catch (AuthenticationException e) {
      return ResponseEntity.status(401).body(e.getMessage());
    }
  }

  @PostMapping("/signup")
  private ResponseEntity<String> register(@RequestBody UserRegisterDto user) {

    try {
      return ResponseEntity.ok(authService.register(user));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(401).body(e.getMessage());
    }

  }

}
