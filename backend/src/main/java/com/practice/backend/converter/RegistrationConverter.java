package com.practice.backend.converter;

import com.practice.backend.dto.auth.RegistrationRequest;
import com.practice.backend.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class RegistrationConverter {

    public User requestToEntity(RegistrationRequest registrationRequest) {
        return User.builder()
                .email(registrationRequest.getEmail())
                .password(registrationRequest.getPassword())
                .phone(registrationRequest.getPhone())
                .firstName(registrationRequest.getFirstName())
                .middleName(registrationRequest.getMiddleName())
                .lastName(registrationRequest.getLastName())
                .build();
    }

}
