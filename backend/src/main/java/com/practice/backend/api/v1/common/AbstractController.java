package com.practice.backend.api.v1.common;

import com.practice.backend.api.v1.exception.HttpRequestMethodNotSupportedException;
import com.practice.backend.converter.BaseConverter;
import com.practice.backend.filtering.common.FilterableProperty;
import com.practice.backend.filtering.common.FilteringOperation;
import com.practice.backend.filtering.exception.IllegalFilteringOperationException;
import com.practice.backend.filtering.common.SearchCriteria;
import com.practice.backend.model.Identifiable;
import com.practice.backend.service.AbstractService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Validated
@SecurityRequirement(name = "Bearer Authentication")
public abstract class AbstractController<
        EntityType extends Identifiable,
        ApiRequestType extends ApiRequest,
        ApiResponseType extends ApiResponse>
        implements FilterableController<EntityType> {

    public static final Integer DEFAULT_PAGE_SIZE = 25;
    public static final Integer DEFAULT_PAGE_INDEX = 0;

    private static final Pattern PATTERN = Pattern.compile("(\\w+?)(:|!_=|[!<>_]=?|=)(.*)");

    //TODO: Don't use field injection.
    @Autowired
    protected ConversionService conversionService;

    public abstract Class<EntityType> getEntityClass();

    public abstract AbstractService<EntityType> getService();

    public abstract BaseConverter<EntityType, ApiRequestType, ApiResponseType> getConverter();

    public abstract ApiResponseType convertEntityToResponse(EntityType entity, List<String> entitiesToExpand);

    @Operation(summary = "Retrieve a list of all records")
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<PageResponse<ApiResponseType>> getAll(
            @RequestParam(required = false)
            Optional<@PositiveOrZero(message = "Page index must have a positive or zero value") Integer> index,

            @RequestParam(required = false)
            Optional<@Range(min = 1, max = 100, message = "Page size must be between 1 and 100 inclusive") Integer> size,

            @RequestParam(required = false) Optional<String> search,

            @PathParam("sort") Sort sort,

            @RequestParam(value = "expand_fields", required = false) Optional<String> expand) {
        int pageSize = size.orElse(DEFAULT_PAGE_SIZE);
        int pageIndex = index.orElse(DEFAULT_PAGE_INDEX);

        Specification<EntityType> filteringSpecification = buildDefaultGetAllFilteringSpec(search);

        sort = mapSortProperties(sort);

        Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);

        Page<EntityType> entitiesPaged = getService().get(filteringSpecification, pageable);

        List<ApiResponseType> responses = entitiesPaged.getContent().stream()
                .map(item -> convertEntityToResponse(item, BaseControllerUtil.parseExpandField(expand))).collect(Collectors.toList());

        PageResponse<ApiResponseType> apiResponseTypePageResponse =
                new PageResponse<>(entitiesPaged.getTotalElements(), entitiesPaged.getNumber(), entitiesPaged.getSize(), responses);

        return ResponseEntity.ok(apiResponseTypePageResponse);
    }

    protected Sort mapSortProperties(Sort sort) {
        if (sort == null) {
            return Sort.unsorted();
        }
        Iterator<Sort.Order> iterator = sort.iterator();
        List<Sort.Order> mappedOrders = new LinkedList<>();
        while (iterator.hasNext()) {
            Sort.Order order = iterator.next();
            Sort.Order mappedOrder = new Sort.Order(
                    order.getDirection(),
                    order.getProperty(),
                    order.getNullHandling());
            mappedOrders.add(mappedOrder);
        }
        return Sort.by(mappedOrders);
    }

    @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ApiResponseType getRecord(@PathVariable(value = "id") Long id,
                                     @RequestParam(value = "expand_fields", required = false) Optional<String> expand) {
        EntityType entity = getService().getOne(id);
        BaseControllerUtil.getObjectOrNotFound(entity, id, getEntityClass());
        return convertEntityToResponse(entity, BaseControllerUtil.parseExpandField(expand));
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Create record")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseType createRecord(@Valid @RequestBody ApiRequestType request) {

        EntityType entity = getConverter().requestToEntity(request);

        entity = executeEntityCreate(entity, request);

        return convertEntityToResponse(entity, expandFieldsOnCreateAndUpdate());
    }

    protected EntityType executeEntityCreate(EntityType entity, ApiRequestType request) {
        return getService().create(entity);
    }

    @PutMapping(path = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseType updateRecord(@PathVariable(value = "id") Long id,
                                        @Valid @RequestBody ApiRequestType request) {
        EntityType existingEntity = getById(id);

        existingEntity = updateEntity(request, existingEntity);

        existingEntity = executeEntityUpdate(existingEntity, request);

        return convertEntityToResponse(existingEntity, expandFieldsOnCreateAndUpdate());
    }

    protected abstract EntityType updateEntity(ApiRequestType request, EntityType entity);

    protected EntityType executeEntityUpdate(EntityType entity, ApiRequestType request) {
        return getService().update(entity);
    }

    @PatchMapping(path = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ApiResponseType patchRecord(
            @PathVariable(value = "id") Long id, @RequestBody Map<String, Object> request) {
        BaseControllerUtil.checkPatchRequest(request);
        EntityType entity = getById(id);

        patchFields(entity, request);

        entity = executeEntityPatch(entity, request);

        return convertEntityToResponse(entity, Collections.emptyList());
    }

    protected EntityType executeEntityPatch(EntityType entity, Map<String, Object> request) {
        return getService().update(entity);
    }

    /**
     * Method to be overridden by implementing Controller if the Http PATCH method is allowed.
     * <p>
     * Abstract implementation throws an unchecked {@link HttpRequestMethodNotSupportedException} which results in Http status code 405 (Method Not Allowed).
     * It is only allowed to PATCH simple fields, that means no fields that are a reference to other entity.
     *
     * @param entity        The entity in which the updates need to be done.
     * @param fieldsToPatch A Map of the JSON fields in the request. These values should be updated in the entity.
     */
    protected void patchFields(EntityType entity, Map<String, Object> fieldsToPatch) {
        throw new HttpRequestMethodNotSupportedException("PATCH");
    }

    @DeleteMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecord(@PathVariable(value = "id") Long id) {
        EntityType entity = getById(id);
        getService().delete(entity);
    }

    public List<String> expandFieldsOnCreateAndUpdate() {
        return Collections.emptyList();
    }

    protected Specification<EntityType> buildDefaultGetAllFilteringSpec(Optional<String> search) {
        if (search.isPresent() && this.getFilterSpecificationsBuilder() != null) {
            List<FilterableProperty<EntityType>> filterableProperties = this.getFilterSpecificationsBuilder().getFilterableProperties();
            List<SearchCriteria> searchCriteria = parseSearchCriteria(search.get(), filterableProperties);
            return this.getFilterSpecificationsBuilder().buildSpecification(searchCriteria);
        }
        return null;
    }

    protected List<SearchCriteria> parseSearchCriteria(String searchQuery, List<FilterableProperty<EntityType>> filterableProperties) {
        String[] searchParams = searchQuery.split(",");
        List<SearchCriteria> searchCriteria = new ArrayList<>();

        for (String searchParameter : searchParams) {
            Matcher matcher = PATTERN.matcher(searchParameter);
            while (matcher.find()) {
                String key = matcher.group(1);
                String operationStr = matcher.group(2);
                FilteringOperation operation = FilteringOperation.fromString(operationStr);
                String value = matcher.group(3);

                Optional<FilterableProperty<EntityType>> filterableProperty = filterableProperties.stream()
                        .filter(property -> property.getPropertyName().equals(key)).findFirst();

                if (filterableProperty.isPresent()) {

                    Object convertedValue;
                    if ("null".equals(value) || StringUtils.isEmpty(value)) {
                        convertedValue = null;
                    } else {
                        convertedValue = convertValueForCriteria(key, operation, value, filterableProperty.get());
                    }
                    // check if a FilterableOperation is supported
                    if (!filterableProperty.get().getOperators().contains(operation)) {
                        throw new IllegalFilteringOperationException("Operation '" + operation + "' is not supported for property " + key);
                    }
                    searchCriteria.add(new SearchCriteria(key, operation, convertedValue));
                } else {
                    log.warn("Filtering on property '{}' has been skipped because it's absent in filterableProperties", key);
                }
            }
        }
        return searchCriteria;
    }

    protected Object convertValueForCriteria(String key, FilteringOperation operation, String value, FilterableProperty<EntityType> filterableProperty) {
        return conversionService.convert(value, filterableProperty.getExpectedType());
    }

    private EntityType getById(Long id) {
        return BaseControllerUtil.getObjectOrNotFound(getService().getOne(id), id, getEntityClass());
    }

    protected Specification<EntityType> additionalGetRecordSpec(HttpServletRequest servletRequest) {
        // need to be Overridden where it's required
        return null;
    }
}