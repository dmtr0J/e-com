package com.practice.backend.repository;

import com.practice.backend.model.Identifiable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseIdentifiableRepository<EntityType extends Identifiable> extends PrimaryRepository<Long, EntityType> {
}
