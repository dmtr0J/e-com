package com.practice.backend.repository;

import com.practice.backend.model.entity.Address;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends BaseIdentifiableRepository<Address> {
}
