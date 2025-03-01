package com.practice.backend.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class RefreshAccessTokenRequest {

    @NotBlank(message = "Refresh token is required")
    private String refreshToken;

}
