package com.practice.backend.config;

import com.practice.backend.model.enums.Role;
import com.practice.backend.security.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.practice.backend.api.v1.ApiConstants.API;
import static com.practice.backend.api.v1.ApiConstants.AUTH_PATH;
import static com.practice.backend.api.v1.ApiConstants.LOGIN_PATH;
import static com.practice.backend.api.v1.ApiConstants.LOGOUT_PATH;
import static com.practice.backend.api.v1.ApiConstants.MATCH_ALL_PATHS;
import static com.practice.backend.api.v1.ApiConstants.PRODUCT_PATH;
import static com.practice.backend.api.v1.ApiConstants.REFRESH_TOKEN_PATH;
import static com.practice.backend.api.v1.ApiConstants.REGISTER_PATH;
import static com.practice.backend.api.v1.ApiConstants.USER_PATH;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   CustomCorsConfiguration corsConfiguration,
                                                   JwtAuthenticationFilter jwtAuthenticationFilter)
            throws Exception {
        return http
                .securityMatcher(API + MATCH_ALL_PATHS)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                AUTH_PATH + REGISTER_PATH,
                                AUTH_PATH + LOGIN_PATH,
                                AUTH_PATH + REFRESH_TOKEN_PATH
                        )
                        .permitAll()
                        .requestMatchers(
                                HttpMethod.PATCH, USER_PATH
                        )
                        .hasAuthority(Role.USER.name())
                        .requestMatchers(
                                USER_PATH + MATCH_ALL_PATHS
                        )
                        .hasAuthority(Role.ADMIN.name())
                        .requestMatchers(
                                AUTH_PATH + LOGOUT_PATH,
                                PRODUCT_PATH + MATCH_ALL_PATHS
                        )
                        .hasAnyAuthority(Role.USER.name(), Role.ADMIN.name())
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfiguration))
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
