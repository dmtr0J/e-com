package com.practice.backend.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.practice.backend.security.exception.InvalidJwtTokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;

@Component
public class JwtUtil {

    private final Algorithm algorithm;

    @Value("${jwt.issuer}") private String issuer;
    @Value("${jwt.expiration.access.millis}") private long accessTokenExpiration;
    @Value("${jwt.expiration.refresh.millis}") private long refreshTokenExpiration;


    public JwtUtil(@Value("${jwt.secret}") String secret) {
        this.algorithm = Algorithm.HMAC256(secret);
    }

    public String generateAccessToken(String subject) {
        return this.generateToken(subject, null, accessTokenExpiration);
    }

    public String generateRefreshToken(String subject) {
        return this.generateToken(subject, null, refreshTokenExpiration);
    }

    public String generateToken(String subject, Map<String, Object> claims, long expirationMillis) {
        return JWT.create()
                .withIssuer(issuer)
                .withSubject(subject)
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusMillis(expirationMillis))
                .withPayload(claims)
                .sign(algorithm);
    }

    public String extractSubject(String token) {
        try {
            String subject = decodeToken(token).getSubject();
            if (subject == null) {
                throw new InvalidJwtTokenException("Invalid subject, cannot be null");
            }
            return subject;
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException("Expired or invalid JWT token");
        }
    }

    public boolean isTokenValid(String token) {
        try {
            decodeToken(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    public Instant extractExpiration(String token) {
        try {
            DecodedJWT jwt = decodeToken(token);
            return jwt.getExpiresAtAsInstant();
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException("Expired or invalid JWT token");
        }
    }

    private DecodedJWT decodeToken(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build();
        return verifier.verify(token);
    }
}
