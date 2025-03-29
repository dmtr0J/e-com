package com.practice.backend.converter;

import com.practice.backend.dto.UserRequest;
import com.practice.backend.dto.UserResponse;
import com.practice.backend.model.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserConverter implements Converter<User, UserRequest, UserResponse> {

    @Override
    public User requestToEntity(UserRequest userRequest) {
        return User.builder()
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .role(userRequest.getRole())
                .phone(userRequest.getPhone())
                .firstName(userRequest.getFirstName())
                .middleName(userRequest.getMiddleName())
                .lastName(userRequest.getLastName())
                .birthDate(userRequest.getBirthDate())
                .build();
    }

    @Override
    public UserResponse entityToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .orders(user.getOrders())
                .phone(user.getPhone())
                .firstName(user.getFirstName())
                .middleName(user.getMiddleName())
                .lastName(user.getLastName())
                .birthDate(user.getBirthDate())
                .build();
    }

    @Override
    public List<UserResponse> entityToResponse(List<User> entity) {
        return entity.stream().map(this::entityToResponse).toList();
    }
}
