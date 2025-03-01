package com.practice.backend.dao;

import com.practice.backend.dao.specification.IdSpecification;
import com.practice.backend.model.Identifiable;
import com.practice.backend.repository.BaseIdentifiableRepository;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public abstract class AbstractIdentifiableDao<EntityType extends Identifiable> extends AbstractDao<Long, EntityType> {

    protected abstract BaseIdentifiableRepository<EntityType> getRepository();

    public Optional<EntityType> getOneById(Long id) {
        Specification<EntityType> specification = new IdSpecification<>(id);
        return getRepository().findOne(specification);

    }

}
