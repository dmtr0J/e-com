package com.practice.backend.service;

import com.practice.backend.converter.RegistrationConverter;
import com.practice.backend.dto.auth.AuthenticationResponse;
import com.practice.backend.dto.auth.LoginRequest;
import com.practice.backend.dto.auth.RefreshAccessTokenRequest;
import com.practice.backend.dto.auth.RegistrationRequest;
import com.practice.backend.model.entity.RefreshToken;
import com.practice.backend.model.entity.RefreshToken_;
import com.practice.backend.model.entity.User;
import com.practice.backend.security.exception.InvalidJwtTokenException;
import com.practice.backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtUtil jwtUtil;
    private final RegistrationConverter registrationConverter;
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticatedUserService authenticatedUserService;

    public AuthenticationResponse register(RegistrationRequest request) {
        User savedUser = userService.create(
                registrationConverter.requestToEntity(request));

        String accessToken = jwtUtil.generateAccessToken(savedUser.getEmail());
        String refreshToken = jwtUtil.generateRefreshToken(savedUser.getEmail());

        refreshTokenService.create(new RefreshToken(
                savedUser,
                refreshToken,
                jwtUtil.extractExpiration(refreshToken)
        ));

        return new AuthenticationResponse(accessToken, refreshToken);
    }

    public AuthenticationResponse login(LoginRequest request) {
        User user = userService.getOneByEmail(request.getEmail());

        RefreshToken refreshToken = refreshTokenService.getOneByField(RefreshToken_.USER, user);

        if (refreshToken != null) {
            refreshTokenService.delete(refreshToken);
        }

        String accessToken = jwtUtil.generateAccessToken(user.getEmail());
        String newRefreshToken = jwtUtil.generateRefreshToken(user.getEmail());

        refreshTokenService.create(new RefreshToken(
                user,
                newRefreshToken,
                jwtUtil.extractExpiration(newRefreshToken)
        ));

        return new AuthenticationResponse(accessToken, newRefreshToken);
    }

    public AuthenticationResponse refreshToken(RefreshAccessTokenRequest request) {
        if (!jwtUtil.isTokenValid(request.getRefreshToken())) {
            throw new InvalidJwtTokenException(
                    "Provided refresh token is expired or failed verification");
        }

        RefreshToken oldRefreshToken = refreshTokenService
                .getOneByField(RefreshToken_.TOKEN, request.getRefreshToken());

        if (oldRefreshToken == null) {
            throw new InvalidJwtTokenException("Provided refresh token is not valid");
        }

        User user = userService.getOneByEmail(
                jwtUtil.extractSubject(oldRefreshToken.getToken()));

        String accessToken = jwtUtil.generateAccessToken(user.getEmail());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());

        return new AuthenticationResponse(accessToken, refreshToken);
    }

    public void logout() {
        User currentUser = authenticatedUserService.getCurrentUser();

        List<RefreshToken> refreshToken = refreshTokenService
                .getByField(RefreshToken_.USER, currentUser);

        refreshTokenService.delete(refreshToken);
    }

}