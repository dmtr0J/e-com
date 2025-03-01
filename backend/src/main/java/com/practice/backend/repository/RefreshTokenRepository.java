package com.practice.backend.repository;

import com.practice.backend.model.entity.RefreshToken;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends BaseIdentifiableRepository<RefreshToken>{
}
