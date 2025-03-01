package com.practice.backend.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthenticationResponse {

    @NotBlank(message = "Access token is required")
    private String accessToken;

    @NotBlank(message = "Refresh token is required")
    private String refreshToken;

}