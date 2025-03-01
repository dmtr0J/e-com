package com.practice.backend.dao.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.List;

public class SpecificationUtil {

    public static <T, U> Path<U> buildPath(Root<T> root, String fieldPath) {
        List<String> pathParts = Arrays.asList(fieldPath.split("\\."));
        Path<U> path = root.get(pathParts.getFirst());
        for (String pathPart : pathParts.subList(1, pathParts.size())) {
            path = path.get(pathPart);
        }
        return path;
    }

    public static <T, U> Join<U, T> buildJoin(Root<T> root, String fieldPath) {
        List<String> joinParts = Arrays.asList(fieldPath.split("\\."));
        Join<U, T> join = root.joinSet(joinParts.getFirst());
        for (String joinPart : joinParts.subList(1, joinParts.size())) {
            join = join.joinSet(joinPart);
        }
        return join;
    }

    public static <T> Specification<T> initEmptySpec() {
        return Specification.where(null);
    }
}
