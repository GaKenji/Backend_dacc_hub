package com.dacchub.backend.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record UserLoginDto(@NotBlank(message = "Email is required") String email,
    @NotBlank(message = "Password is required") String password) {
}
