package com.practice.backend.service;

import com.practice.backend.dao.AbstractIdentifiableDao;
import com.practice.backend.dao.exception.EntityNotFoundException;
import com.practice.backend.model.Identifiable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public abstract class AbstractService<EntityType extends Identifiable> {

    protected abstract AbstractIdentifiableDao<EntityType> getDao();

    public Page<EntityType> get(Pageable pageable) {
        return getDao().getAll(pageable);
    }

    public List<EntityType> get() {
        return getDao().getAll();
    }

    public List<EntityType> get(Sort sort) {
        return getDao().getAll(sort);
    }

    public Page<EntityType> get(Specification<EntityType> filter, Pageable pageable) {
        return getDao().getAll(filter, pageable);
    }

    public List<EntityType> get(Specification<EntityType> filter) {
        return getDao().getAll(filter);
    }

    public List<EntityType> get(Specification<EntityType> filter, Sort sort) {
        return getDao().getAll(filter, sort);
    }

    public List<EntityType> getByField(String field, Object value) {
        return getDao().getAllByField(field, value);
    }

    public EntityType getOne(Long id) {
        return getDao().getOneById(id).orElseThrow(
                () -> new EntityNotFoundException("Entity not found with id: " + id)
        );
    }

    public EntityType getOne(Specification<EntityType> filter) {
        return getDao().getOne(filter);
    }

    public EntityType getOneByField(String field, Object value) {
        return getDao().getOneByField(field, value);
    }

    @Transactional
    public EntityType create(EntityType entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Can not create entity which is null" );
        }

        beforeCreate(entity);
        entity = getDao().create(entity);
        afterCreate(entity);
        return entity;
    }

    /**
     * Method to override when additional create logic is needed
     *
     * @param entity the entity to be created
     */
    protected void beforeCreate(EntityType entity) {
    }

    /**
     * Method to override when additional create logic is needed
     *
     * @param entity the entity to be created
     */
    protected void afterCreate(EntityType entity) {
    }


    @Transactional
    public EntityType update(EntityType entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Can not update entity which is null");
        }

        beforeUpdate(entity);
        entity = getDao().update(entity);
        afterUpdate(entity);
        return entity;
    }

    /**
     * Method to override when additional update logic is needed
	 *
     * @param entity the entity to be updated
	 */
    protected void beforeUpdate(EntityType entity) {
    }

    /**
     * Method to override when additional update logic is needed
     *
     * @param entity the entity to be updated
     */
    protected void afterUpdate(EntityType entity) {
    }

    @Transactional
    public void delete(Specification<EntityType> specification) {
        EntityType entity = getDao().getOne(specification);
        if (entity == null) {
            throw new IllegalArgumentException(
                    "Can not delete by specification for class. "
            );
        }

        getDao().delete(entity);
    }

    @Transactional
    public void delete(EntityType entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Can not delete entity which is null");
        }

        getDao().delete(entity);
    }

    @Transactional
    public void delete(Iterable<EntityType> entities) {
        if (entities == null || !entities.iterator().hasNext()) {
            throw new IllegalArgumentException("Can not delete entities which is null or empty");
        }

        getDao().deleteAll(entities);
    }

}

