package com.practice.backend.dao;

import com.practice.backend.dao.specification.EqualSpecification;
import com.practice.backend.model.PrimaryEntity;
import com.practice.backend.repository.PrimaryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import java.util.List;

public abstract class AbstractDao<IdType, EntityType extends PrimaryEntity<IdType>> {

    protected abstract PrimaryRepository<IdType, EntityType> getRepository();

    public List<EntityType> getAll() {
        return getRepository().findAll();
    }

    public Page<EntityType> getAll(Pageable pageable) {
        return getRepository().findAll(pageable);
    }

    public List<EntityType> getAll(Sort sort) {
        return getRepository().findAll(sort);
    }

    public List<EntityType> getAll(Specification<EntityType> filter) {
        return getRepository().findAll(filter);
    }

    public Page<EntityType> getAll(Specification<EntityType> filter, Pageable pageable) {
        return getRepository().findAll(filter, pageable);
    }

    public List<EntityType> getAll(Specification<EntityType> filter, Sort sort) {
        return getRepository().findAll(filter, sort);
    }

    public List<EntityType> getAllByField(String field, Object value) {
        Specification<EntityType> filter = new EqualSpecification<>(field, value);
        return this.getAll(filter);
    }

    public EntityType getOne(Specification<EntityType> filter) {
        return getRepository().findOne(filter).orElse(null);
    }

    public EntityType getOneByField(String field, Object value) {
        Specification<EntityType> filter = new EqualSpecification<>(field, value);
        return this.getOne(filter);
    }

    public EntityType getFirst(Specification<EntityType> filter) {
        List<EntityType> result = getRepository().findAll(filter);
        return result.isEmpty() ? null : result.getFirst();
    }

    public EntityType create(EntityType entity) {
        return getRepository().save(entity);
    }

    public EntityType update(EntityType entity) {
        return getRepository().save(entity);
    }

    public void delete(EntityType entity) {
        getRepository().deleteById(entity.getId());
    }

    public void deleteAll(Iterable<EntityType> entities) {
        getRepository().deleteAll(entities);
    }

}