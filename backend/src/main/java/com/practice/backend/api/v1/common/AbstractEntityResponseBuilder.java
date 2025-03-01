package com.practice.backend.api.v1.common;

import com.practice.backend.api.v1.exception.InternalServerException;
import com.practice.backend.model.Identifiable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.GenericTypeResolver;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public abstract class AbstractEntityResponseBuilder<T extends Identifiable, R extends ApiResponse> implements EntityResponseBuilder<T, R> {

    @Override
    public R convertEntityToResponse(T entity) {
        return convertEntityToResponse(entity, null);
    }

    @Override
    public R convertEntityToResponse(T entity, List<String> entitiesToExpand) {
        if (entity == null) return null;

        try {
            Class< R > responseTypeClass = getResponseTypeClass();
            R response = responseTypeClass.getConstructor().newInstance();
            mapFields(response, entity);
            expandEntities(response, entity, entitiesToExpand);

            return response;
        } catch (IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException |
                 InstantiationException | IllegalAccessException e ) {
            throw new InternalServerException("Error creating response instance of type " + entity.getClass());
        }
    }

    protected abstract void mapFields(R response, T entity);

    protected abstract void buildLinks(R response, T entity);

    protected abstract void expandEntities(R response, T entity, List<String> entitiesToExpand);

    public <S> void expandEntity(S entity, List<String> entitiesToExpand, String expandFieldName, Consumer<List<String>> consumer) {
        if (entitiesToExpand == null || entitiesToExpand.isEmpty()) return;
        if (expandFieldName == null) return;
        if (entity == null) return;

        boolean found = entitiesToExpand.stream().anyMatch(s -> s.startsWith(expandFieldName));

        if (found) {
            consumer.accept(stripBaseFromExpandFields(entitiesToExpand, expandFieldName));
        }
    }

    private List<String> stripBaseFromExpandFields(List<String> expandFields, String base) {
        if (expandFields == null) return null;

        if (StringUtils.isEmpty(base)) return expandFields;

        return expandFields.stream()
                .filter(s -> s.startsWith(base + "."))
                .map(s -> s.replaceFirst("^" + base + ".", ""))
                .filter(StringUtils::isNotEmpty)
                .collect(Collectors.toList());
    }

    private Class<R> getResponseTypeClass() {
        Class<?>[] classes = GenericTypeResolver.resolveTypeArguments(getClass(), AbstractEntityResponseBuilder.class);
        assert classes != null;
        return (Class<R>) classes[1];
    }

}