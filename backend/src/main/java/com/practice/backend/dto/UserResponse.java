package com.practice.backend.dto;

import com.practice.backend.api.v1.common.ApiResponse;
import com.practice.backend.model.entity.Order;
import com.practice.backend.model.enums.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class UserResponse implements ApiResponse {

    private Long id;
    private String email;
    private Role role;
    private String phone;
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate birthDate;
    private List<Order> orders;

}
