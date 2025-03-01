package com.practice.backend.config;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PATCH;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpMethod.DELETE;

@Configuration
public class CustomCorsConfiguration implements CorsConfigurationSource {

    @Value("${frontend.url}") private String frontendUrl;

    @Override
    public CorsConfiguration getCorsConfiguration(@NonNull HttpServletRequest request) {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(frontendUrl));
        config.setAllowedMethods(List.of(GET.name(), POST.name(), PUT.name(), DELETE.name(), PATCH.name()));
        config.setAllowCredentials(true);
        config.setAllowedHeaders(List.of(CorsConfiguration.ALL));
        return config;
    }
}
