package com.practice.backend.dao;

import com.practice.backend.model.entity.User;
import com.practice.backend.repository.BaseIdentifiableRepository;
import com.practice.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDao extends AbstractIdentifiableDao<User> {

    private final UserRepository userRepository;

    @Override
    protected BaseIdentifiableRepository<User> getRepository() {
        return this.userRepository;
    }
}