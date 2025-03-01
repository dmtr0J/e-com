package com.practice.backend.service;

import com.practice.backend.dao.AbstractIdentifiableDao;
import com.practice.backend.dao.UserDao;
import com.practice.backend.dao.exception.EntityNotFoundException;
import com.practice.backend.model.entity.User;
import com.practice.backend.model.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService extends AbstractService<User> {

    private final UserDao dao;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected AbstractIdentifiableDao<User> getDao() {
        return this.dao;
    }

    @Override
    protected void beforeCreate(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
    }

    public User getOneByEmail(String email) {
        User user = getDao().getOneByField("email", email);

        if (user == null) {
            throw new EntityNotFoundException("User not found with: " + email);
        }

        return user;

    }
}
