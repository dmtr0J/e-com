package com.practice.backend.dao;

import com.practice.backend.model.entity.RefreshToken;
import com.practice.backend.repository.BaseIdentifiableRepository;
import com.practice.backend.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenDao extends AbstractIdentifiableDao<RefreshToken> {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    protected BaseIdentifiableRepository<RefreshToken> getRepository() {
        return this.refreshTokenRepository;
    }
}
