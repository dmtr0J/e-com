package com.practice.backend.repository;

import com.practice.backend.model.PrimaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface PrimaryRepository<Id, Entity extends PrimaryEntity<Id>> extends
        JpaRepository<Entity, Id>,
        JpaSpecificationExecutor<Entity>,
        PagingAndSortingRepository<Entity, Id> {
}