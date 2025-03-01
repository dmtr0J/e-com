package com.practice.backend.dao.specification;

import com.practice.backend.model.PrimaryEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@AllArgsConstructor
public class IdSpecification<IdType, EntityType extends PrimaryEntity<IdType>> implements Specification<EntityType> {

    private final IdType id;

    private boolean inverse = false;

    public IdSpecification(IdType id) {
        this.id = id;
    }

    @Override
    public Predicate toPredicate(Root<EntityType> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (!inverse) {
            return criteriaBuilder.equal(root.get("id"), id);
        }
        return criteriaBuilder.notEqual(root.get("id"), (id));
    }
}