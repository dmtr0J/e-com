package com.practice.backend.api.v1.controller;

import com.practice.backend.api.v1.ApiConstants;
import com.practice.backend.api.v1.common.AbstractController;
import com.practice.backend.converter.Converter;
import com.practice.backend.converter.UserConverter;
import com.practice.backend.dto.UserRequest;
import com.practice.backend.dto.UserResponse;
import com.practice.backend.filtering.UserSpecificationBuilder;
import com.practice.backend.filtering.specification.EntityFilterSpecificationBuilder;
import com.practice.backend.model.entity.User;
import com.practice.backend.service.AbstractService;
import com.practice.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConstants.USER_PATH)
public class UserController extends AbstractController<User, UserRequest, UserResponse> {

    private final UserService userService;
    private final UserConverter userConverter;
    private final UserSpecificationBuilder userSpecificationBuilder;

    @Override
    public AbstractService<User> getService() {
        return this.userService;
    }

    @Override
    public Converter<User, UserRequest, UserResponse> getConverter() {
        return this.userConverter;
    }

    @Override
    public UserResponse convertEntityToResponse(User user, List<String> entitiesToExpand) {
        return getConverter().entityToResponse(user);
    }

    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    protected User updateEntity(UserRequest request, User user) {
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());
        user.setPhone(request.getPhone());
        user.setFirstName(request.getFirstName());
        user.setMiddleName(request.getMiddleName());
        user.setLastName(request.getLastName());
        user.setBirthDate(request.getBirthDate());
        return user;
    }

    @Override
    public EntityFilterSpecificationBuilder<User> getFilterSpecificationsBuilder() {
        return this.userSpecificationBuilder;
    }

    //TODO
//    @Override
//    protected void patchFields(User user, Map<String, Object> fieldsToPatch) {
//
//    }

    @GetMapping("/exist")
    public boolean existsByEmail(@RequestParam /*@Email @NotBlank*/ String email) {
        return this.userService.existsByEmail(email);
    }
}