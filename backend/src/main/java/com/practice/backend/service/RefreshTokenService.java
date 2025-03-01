package com.practice.backend.service;

import com.practice.backend.dao.AbstractIdentifiableDao;
import com.practice.backend.dao.RefreshTokenDao;
import com.practice.backend.model.entity.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService extends AbstractService<RefreshToken> {

    private final RefreshTokenDao refreshTokenDAO;

    @Override
    protected AbstractIdentifiableDao<RefreshToken> getDao() {
        return this.refreshTokenDAO;
    }
}
