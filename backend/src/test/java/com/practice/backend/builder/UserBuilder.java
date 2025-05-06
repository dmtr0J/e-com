package com.practice.backend.builder;

import com.practice.backend.model.entity.Order;
import com.practice.backend.model.entity.User;
import com.practice.backend.model.enums.Role;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class UserBuilder {

    private User user;

    public UserBuilder() {
        initDefaultData();
    }

    public static UserBuilder start() {
        return new UserBuilder();
    }

    public User build() {
        User user = this.user;
        initDefaultData();
        return user;
    }

    private void initDefaultData() {
        long randomValue = ThreadLocalRandom.current().nextLong(1, 999999);
        this.user = User.builder()
                .id(randomValue)
                .email("email" + randomValue + "@test.com")
                .password("password" + randomValue)
                .role(Role.USER)
                .phone("+1" + (randomValue + 1000000000))
                .firstName("firstName" + randomValue)
                .middleName("middleName" + randomValue)
                .lastName("lastName" + randomValue)
                .birthDate(LocalDate.now().minusYears(1))
                .build();
    }

    public UserBuilder setId(Long id) {
        this.user.setId(id);
        return this;
    }

    public UserBuilder setEmail(String email) {
        this.user.setEmail(email);
        return this;
    }

    public UserBuilder setPassword(String password) {
        this.user.setPassword(password);
        return this;
    }

    public UserBuilder setRole(Role role) {
        this.user.setRole(role);
        return this;
    }

    public UserBuilder setOrders(List<Order> orders) {
        this.user.setOrders(orders);
        return this;
    }

    public UserBuilder setPhone(String phone) {
        this.user.setPhone(phone);
        return this;
    }

    public UserBuilder setFirstName(String firstName) {
        this.user.setFirstName(firstName);
        return this;
    }

    public UserBuilder setMiddleName(String middleName) {
        this.user.setMiddleName(middleName);
        return this;
    }

    public UserBuilder setLastName(String lastName) {
        this.user.setLastName(lastName);
        return this;
    }

    public UserBuilder setBirthDate(LocalDate birthDate) {
        this.user.setBirthDate(birthDate);
        return this;
    }
}
