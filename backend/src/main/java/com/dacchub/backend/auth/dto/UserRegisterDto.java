package com.dacchub.backend.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;
import java.util.UUID;

public record UserRegisterDto(
    @NotBlank(message = "Name is required") String name,

    @NotBlank(message = "Email is required") @Email(message = "Invalid email format") String email,

    @NotBlank(message = "Password is required") @Size(min = 8, message = "Password must be at least 8 characters long") String password,

    @URL(message = "Invalid Avatar URL") String avatarUrl,

    String bio,

    @URL(message = "Invalid GitHub URL") String githubUrl,

    @URL(message = "Invalid LinkedIn URL") String linkedinUrl,

    @URL(message = "Invalid Portfolio URL") String portfolioUrl,

    UUID courseId) {
}
