package com.practice.backend.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.practice.backend.util.JwtUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles(
        {"develop"}
)
public class JwtUtilTests {

    @Autowired
    private JwtUtil jwtUtil;
    private final Map<String, Object> claims = new HashMap<>();
    private final long expirationTime = 1000 * 15 * 60; // 15 minutes

    @BeforeEach
    public void setup() {
        claims.put("username", "John");
    }

    @Test
    public void whenGenerateTokenThenReturnValidToken() throws JSONException {
        String token = jwtUtil.generateToken("testSubject", claims, expirationTime);

        assertNotNull(token, "Generated token must not be null");

        String[] tokenParts = token.split("\\.");
        assertTrue(tokenParts.length == 3, "Token must consist of 3 parts (header, payload, signature)");

        String payload = tokenParts[1];
        String decodedPayload = new String(Base64.getUrlDecoder().decode(payload));

        JSONObject jsonPayload = new JSONObject(decodedPayload);

        assertEquals("John", jsonPayload.get("username"), "The 'username' claim must be 'John'");
    }

    @Test
    public void whenExtractSubjectThenReturnValidSubject() {
        String token = jwtUtil.generateToken("testSubject", claims, expirationTime);
        String subject = jwtUtil.extractSubject(token);

        assertNotNull(subject, "Extracted subject must not be null");
        assertEquals("testSubject", subject);
    }

    @Test
    public void whenTokenIsValidThenReturnTrue() {
        String token = jwtUtil.generateToken("testSubject", claims, expirationTime);
        boolean isTokenValid = jwtUtil.isTokenValid(token);

        assertTrue(isTokenValid, "Valid token must return true");
    }

    @Test
    public void whenTokenIsInvalidThenReturnFalse() {
        String invalidToken = "someInvalidToken";
        boolean isTokenValid = jwtUtil.isTokenValid(invalidToken);

        assertFalse(isTokenValid, "Invalid token must return false");
    }

    @Test
    public void whenDecodeTokenThenReturnDecodedToken() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, JSONException {
        String token = jwtUtil.generateToken("testSubject", claims, expirationTime);
        Method decodeTokenMethod = JwtUtil.class.getDeclaredMethod("decodeToken", String.class);
        decodeTokenMethod.setAccessible(true);

        DecodedJWT decodedJWT = (DecodedJWT) decodeTokenMethod.invoke(jwtUtil, token);

        String decodedPayload = new String(Base64.getUrlDecoder().decode(decodedJWT.getPayload()));

        JSONObject jsonPayload = new JSONObject(decodedPayload);

        assertEquals("John", jsonPayload.get("username"), "The 'username' claim must be 'John'");
    }
}
