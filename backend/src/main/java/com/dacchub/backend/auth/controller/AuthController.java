package com.dacchub.backend.auth.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dacchub.backend.auth.util.JwtUtil;
import com.dacchub.backend.user.entity.User;
import com.dacchub.backend.user.repository.UserRepository;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

  private AuthenticationManager authenticationManager;

  private JwtUtil jwtUtil;

  private UserRepository userRepository;

  public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository userRepository) {
    this.authenticationManager = authenticationManager;
    this.jwtUtil = jwtUtil;
    this.userRepository = userRepository;
  }

  @PostMapping("/login")
  private String login(@RequestBody User user) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword()));

    final UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    return jwtUtil.generateToken(userDetails.getUsername());
  }

  @PostMapping("/register")
  private String register(@RequestBody User user) {
    if (userRepository.existsByUsername(user.getName())) {
      return "User already exists!";
    }

    userRepository.save(user);
    return "User registered successfully!";
  }

}
