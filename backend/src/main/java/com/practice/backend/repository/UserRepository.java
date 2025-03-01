package com.practice.backend.repository;

import com.practice.backend.model.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseIdentifiableRepository<User> {
}
