package com.practice.backend.service;

import com.practice.backend.dao.AbstractIdentifiableDao;
import com.practice.backend.dao.AddressDao;
import com.practice.backend.model.entity.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService extends AbstractService<Address> {

    private final AddressDao dao;

    @Override
    protected AbstractIdentifiableDao<Address> getDao() {return this.dao; }
}
