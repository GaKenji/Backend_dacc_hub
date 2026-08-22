package com.dacchub.backend.auth.entity;

import java.util.UUID;

import com.dacchub.backend.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class RefreshToken {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String token;

  @Column(name = "is_logged_out")
  private boolean loggedOut;

  @NotBlank
  private User user;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public boolean isLoggedOut() {
    return loggedOut;
  }

  public void setLoggedOut(boolean loggedOut) {
    this.loggedOut = loggedOut;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

}
