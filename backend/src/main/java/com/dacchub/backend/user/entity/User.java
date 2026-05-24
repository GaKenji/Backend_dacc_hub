package com.dacchub.backend.user.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.dacchub.backend.course.entity.Course;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;

@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotBlank
  private String name;

  @NotBlank
  @Column(unique = true)
  private String email;

  private String password;

  private String avatarUrl;

  @Column(columnDefinition = "TEXT")
  private String bio;

  private String githubUrl;

  private String linkedinUrl;

  private String portfolioUrl;

  @ManyToOne
  @JoinColumn(name = "course_id")
  private Course course;

  @CreationTimestamp
  private LocalDateTime createdAt;

  public User() {
  }

  public User(String name, String email, String password, String avatarUrl, String bio,
      String githubUrl, String linkedinUrl, String portfolioUrl,
      Course course) {
    this.name = name;
    this.email = email;
    this.password = password;
    this.avatarUrl = avatarUrl;
    this.bio = bio;
    this.githubUrl = githubUrl;
    this.linkedinUrl = linkedinUrl;
    this.portfolioUrl = portfolioUrl;
    this.course = course;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getAvatarUrl() {
    return avatarUrl;
  }

  public void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
  }

  public String getBio() {
    return bio;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }

  public String getGithubUrl() {
    return githubUrl;
  }

  public void setGithubUrl(String githubUrl) {
    this.githubUrl = githubUrl;
  }

  public String getLinkedinUrl() {
    return linkedinUrl;
  }

  public void setLinkedinUrl(String linkedinUrl) {
    this.linkedinUrl = linkedinUrl;
  }

  public String getPortfolioUrl() {
    return portfolioUrl;
  }

  public void setPortfolioUrl(String portfolioUrl) {
    this.portfolioUrl = portfolioUrl;
  }

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

}
