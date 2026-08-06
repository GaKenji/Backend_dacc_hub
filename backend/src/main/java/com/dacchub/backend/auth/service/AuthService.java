package com.dacchub.backend.auth.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dacchub.backend.auth.dto.UserLoginDto;
import com.dacchub.backend.auth.dto.UserRegisterDto;
import com.dacchub.backend.auth.util.JwtUtil;
import com.dacchub.backend.user.entity.User;
import com.dacchub.backend.user.repository.UserRepository;

@Service
public class AuthService {

  private PasswordEncoder passwordEncoder;

  private UserRepository userRepository;

  private AuthenticationManager authenticationManager;

  private JwtUtil jwtUtil;

  public String register(UserRegisterDto dto) {
    if (userRepository.existsByEmail(dto.email())) {
      throw new IllegalArgumentException("Email already exists");
    }

    User user = new User();
    user.setName(dto.name());
    user.setEmail(dto.email());
    user.setPassword(passwordEncoder.encode(dto.password()));
    user.setAvatarUrl(dto.avatarUrl());
    user.setBio(dto.bio());
    user.setGithubUrl(dto.githubUrl());
    user.setLinkedinUrl(dto.linkedinUrl());
    user.setPortfolioUrl(dto.portfolioUrl());
    userRepository.save(user);

    return jwtUtil.generateToken(user.getEmail());
  }

  public String login(UserLoginDto user) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(user.email(), user.password()));

    final UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    return jwtUtil.generateToken(userDetails.getUsername());
  }
}
