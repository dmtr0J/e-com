package com.practice.backend.api.v1.controller;

import com.practice.backend.api.v1.ApiConstants;
import com.practice.backend.dto.auth.AuthenticationResponse;
import com.practice.backend.dto.auth.LoginRequest;
import com.practice.backend.dto.auth.RefreshAccessTokenRequest;
import com.practice.backend.dto.auth.RegistrationRequest;
import com.practice.backend.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConstants.AUTH_PATH)
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(ApiConstants.REGISTER_PATH)
    public AuthenticationResponse register(@RequestBody @Valid RegistrationRequest request) {
        return authenticationService.register(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(ApiConstants.LOGIN_PATH)
    public AuthenticationResponse login(@RequestBody @Valid LoginRequest request) {
        return authenticationService.login(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(ApiConstants.REFRESH_TOKEN_PATH)
    public AuthenticationResponse refreshToken(@RequestBody @Valid RefreshAccessTokenRequest request) {
        return authenticationService.refreshToken(request);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(ApiConstants.LOGOUT_PATH)
    public void logout() {
        authenticationService.logout();
    }
}