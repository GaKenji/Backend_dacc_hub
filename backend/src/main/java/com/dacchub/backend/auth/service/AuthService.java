package com.dacchub.backend.auth.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dacchub.backend.auth.dto.AuthResponseDto;
import com.dacchub.backend.auth.dto.UserLoginDto;
import com.dacchub.backend.auth.dto.UserRegisterDto;
import com.dacchub.backend.auth.entity.RefreshToken;
import com.dacchub.backend.auth.repository.RefreshTokenRepository;
import com.dacchub.backend.auth.util.JwtUtil;
import com.dacchub.backend.course.entity.Course;
import com.dacchub.backend.course.repository.CourseRepository;
import com.dacchub.backend.user.entity.User;
import com.dacchub.backend.user.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthService {

  @Value("${jwt.refresh-expiration}")
  private long refreshExpiration;

  private PasswordEncoder passwordEncoder;

  private UserRepository userRepository;

  private AuthenticationManager authenticationManager;

  private JwtUtil jwtUtil;

  private CourseRepository courseRepository;

  private RefreshTokenRepository refreshTokenRepository;

  public AuthService(PasswordEncoder passwordEncoder, UserRepository userRepository,
      AuthenticationManager authenticationManager, JwtUtil jwtUtil, CourseRepository courseRepository,
      RefreshTokenRepository refreshTokenRepository) {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
    this.authenticationManager = authenticationManager;
    this.jwtUtil = jwtUtil;
    this.courseRepository = courseRepository;
    this.refreshTokenRepository = refreshTokenRepository;
  }

  public AuthResponseDto register(UserRegisterDto dto) {
    if (userRepository.existsByEmail(dto.email())) {
      throw new IllegalArgumentException("Email already exists");
    }

    User user = new User();
    if (dto.courseId() != null) {
      Course course = courseRepository.findById(dto.courseId())
          .orElseThrow(() -> new IllegalArgumentException("Course not found"));
      user.setCourse(course);
    }
    user.setName(dto.name());
    user.setEmail(dto.email());
    user.setPassword(passwordEncoder.encode(dto.password()));
    user.setAvatarUrl(dto.avatarUrl());
    user.setBio(dto.bio());
    user.setGithubUrl(dto.githubUrl());
    user.setLinkedinUrl(dto.linkedinUrl());
    user.setPortfolioUrl(dto.portfolioUrl());

    userRepository.save(user);

    String refreshToken = createAndSaveRefreshToken(user);

    return new AuthResponseDto(jwtUtil.generateToken(user.getEmail()), refreshToken);
  }

  public AuthResponseDto login(UserLoginDto userDto) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(userDto.email(), userDto.password()));

    final UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    User user = userRepository.findByEmail(userDetails.getUsername());

    String refreshToken = createAndSaveRefreshToken(user);

    return new AuthResponseDto(jwtUtil.generateToken(user.getEmail()), refreshToken);
  }

  public String refreshToken(HttpServletRequest request, HttpServletResponse response) {
    String refresh = jwtUtil.parseJwt(request);

    if (refresh == null || !jwtUtil.validateJwtToken(refresh) || !"REFRESH".equals(jwtUtil.getTokenType(refresh)))
      throw new IllegalArgumentException("Invalid token");

    RefreshToken token = refreshTokenRepository.findById(jwtUtil.getTokenIdFromToken(refresh))
        .orElseThrow(() -> new IllegalArgumentException("Invalid token"));

    if (token.isLoggedOut() || token.getExpiresAt() == null || token.getExpiresAt().isBefore(Instant.now())) {
      throw new IllegalArgumentException("Expired token");
    }

    return jwtUtil.generateToken(jwtUtil.getUserFromToken(refresh));
  }

  public String logout(HttpServletRequest request, HttpServletResponse response) {
    String refresh = jwtUtil.parseJwt(request);

    if (refresh == null || !jwtUtil.validateJwtToken(refresh) || !"REFRESH".equals(jwtUtil.getTokenType(refresh))) {
      throw new IllegalArgumentException("Invalid token");
    }

    RefreshToken token = refreshTokenRepository.findById(jwtUtil.getTokenIdFromToken(refresh))
        .orElseThrow(() -> new IllegalArgumentException("Invalid token"));

    if (token.isLoggedOut()) {
      throw new IllegalArgumentException("Already logged out");
    }

    token.setLoggedOut(true);
    refreshTokenRepository.save(token);

    return "Logged out";
  }

  @Transactional
  public String logoutAll(HttpServletRequest request, HttpServletResponse response) {
    String userEmail = null;

    if (request.getUserPrincipal() != null) {
      userEmail = request.getUserPrincipal().getName();
    } else {
      String token = jwtUtil.parseJwt(request);
      if (token != null && jwtUtil.validateJwtToken(token)) {
        userEmail = jwtUtil.getUserFromToken(token);
      }
    }

    if (userEmail == null) {
      throw new IllegalArgumentException("Invalid or missing token");
    }

    long deletedCount = refreshTokenRepository.revokeAllByUserEmail(userEmail);

    if (deletedCount < 1) {
      throw new IllegalArgumentException("No active session found");
    }

    return "Logged out";
  }

  private String createAndSaveRefreshToken(User user) {
    UUID tokenId = UUID.randomUUID();
    String tokenString = jwtUtil.generateRefreshToken(user.getEmail(),
        tokenId);

    RefreshToken refreshToken = new RefreshToken();
    refreshToken.setId(tokenId);
    refreshToken.setUser(user);
    refreshToken.setToken(tokenString);
    refreshToken.setExpiresAt(Instant.now().plusMillis(refreshExpiration));
    refreshToken.setLoggedOut(false);

    refreshTokenRepository.save(refreshToken);
    return tokenString;
  }

}
