package com.practice.backend.dao;

import com.practice.backend.model.entity.Address;
import com.practice.backend.repository.AddressRepository;
import com.practice.backend.repository.BaseIdentifiableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressDao extends AbstractIdentifiableDao<Address> {

    private final AddressRepository addressRepository;

    @Override
    protected BaseIdentifiableRepository<Address> getRepository() {return this.addressRepository; }
}
