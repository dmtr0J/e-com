package com.practice.backend.api.v1.controller.common;

import com.practice.backend.api.v1.common.ApiRequest;
import com.practice.backend.api.v1.common.ApiResponse;
import com.practice.backend.api.v1.common.PageResponse;
import com.practice.backend.converter.Converter;
import com.practice.backend.filtering.common.FilterableProperty;
import com.practice.backend.filtering.common.SearchCriteria;
import com.practice.backend.filtering.specification.EntityFilterSpecificationBuilder;
import com.practice.backend.model.Identifiable;
import com.practice.backend.service.AbstractService;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.stubbing.OngoingStubbing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public abstract class AbstractControllerTest<
        EntityType extends Identifiable,
        ApiRequestType extends ApiRequest,
        ApiResponseType extends ApiResponse> extends BaseControllerTest {

    protected static final Logger log = LoggerFactory.getLogger(AbstractControllerTest.class);

    protected ResultCaptor<EntityType> resultCaptor;

    protected abstract boolean isPatchingAllowed();

    protected abstract Converter<EntityType, ApiRequestType, ApiResponseType> getConverter();

    protected abstract AbstractService<EntityType> getService();

    protected abstract EntityFilterSpecificationBuilder<EntityType> getSpecificationsBuilder();

    protected abstract ApiResponseType convertToResponse(EntityType entity);

    protected abstract ApiRequestType convertToRequest(EntityType entity);

    protected abstract EntityType getNewEntity();

    protected abstract ApiRequestType getNewRequest();

    protected abstract Class<EntityType> getEntityClass();

    protected abstract Class<ApiRequestType> getRequestClass();

    protected abstract String getControllerPath();

    protected abstract List<Function<EntityType, Object>> getUpdatableFieldGetters();

    /**
     * map key (String) - value to be tested<p>
     * map value (Sort) - expected result
     */
    protected abstract Map<String, Sort> getSortingTestParameters();

    /**
     * map key (List of Strings) - value to be tested<p>
     * map value (List of SearchCriteria) - expected result
     */
    protected abstract Map<List<String>, List<SearchCriteria>> getFilteringTestParameters();

    protected abstract List<FilterableProperty<EntityType>> getFilterableProperties();

    @BeforeEach
    public void setup(){
        resultCaptor = new ResultCaptor<>();
    }

    @Test
    public void testGetAll() throws Exception {
        List<EntityType> entities = Arrays.asList(
                getNewEntity(), getNewEntity(), getNewEntity());

        Page<EntityType> page = mockPage(entities);

        List<ApiResponseType> responses = entities
                .stream().map(this::convertToResponse).toList();

        PageResponse<ApiResponseType> expectedResponse = new PageResponse<>(
                page.getTotalElements(),
                page.getNumber(),
                page.getSize(),
                responses);

        mockMvc.perform(get(getControllerPath())
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(expectedResponse)));
    }

    @Test
    public void testDefaultPageIndexAndSizeForGetAll() throws Exception {
        mockPage();

        mockMvc.perform(get(getControllerPath())
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        assertEquals(0, getLastCapturedPageRequest().getPageNumber());
        assertEquals(25, getLastCapturedPageRequest().getPageSize());
    }

    @Test
    public void testCustomPageIndexAndSizeForGetAll() throws Exception {
        mockPage();

        mockMvc.perform(get(getControllerPath())
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .param("index", "7")
                        .param("size", "49"))
                .andExpect(status().isOk());

        assertEquals(7, getLastCapturedPageRequest().getPageNumber());
        assertEquals(49, getLastCapturedPageRequest().getPageSize());
    }

    @Test
    public void testPageSizeValidationForGetAll() throws Exception {
        mockMvc.perform(get(getControllerPath())
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .param("size", "101"))
                .andExpect(status().is4xxClientError());

        mockMvc.perform(get(getControllerPath())
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .param("size", "-1"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testPageIndexValidationForGetAll() throws Exception {
        mockMvc.perform(get(getControllerPath())
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .param("index", "-1"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testGetAllSorting() {
        mockPage();

        Map<String, Sort> sortingProperties = getSortingTestParameters();
        sortingProperties.forEach(this::doSortingTest);
    }

    protected void doSortingTest(String sortingValue, Sort expectedResult) {
        try {
            mockMvc.perform(get(getControllerPath())
                            .accept(MediaType.APPLICATION_JSON_VALUE)
                            .param("sort", sortingValue))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            log.error("Failed getAllSortingTest() test.", e);
            fail();
        }
        assertEquals(expectedResult, getLastCapturedPageRequest().getSort());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testFilteringForGetAll() {
        mockPage();

        Specification<EntityType> specificationMock = mock(Specification.class);

        when(getSpecificationsBuilder().getFilterableProperties())
                .thenReturn(getFilterableProperties());
        when(getSpecificationsBuilder().buildSpecification(anyList()))
                .thenReturn(specificationMock);

        Map<List<String>, List<SearchCriteria>> filteringTestParameters =
                getFilteringTestParameters();
        filteringTestParameters.forEach(this::doFilteringTest);
    }

    @SuppressWarnings("unchecked")
    protected void doFilteringTest(List<String> filteringValues,
                                   List<SearchCriteria> expectedSearchCriteriaList) {
        MockHttpServletRequestBuilder getRequest =
                MockMvcRequestBuilders.get(getControllerPath());

        filteringValues.forEach(filteringValue ->
                getRequest.param("search", filteringValue));

        try {
            mockMvc.perform(getRequest)
                    .andExpect(status().isOk());
        } catch (Exception e) {
            log.error("Failed testFilteringForGetAll() test.", e);
            fail();
        }

        ArgumentCaptor<List<SearchCriteria>> searchCriteriaCaptor = ArgumentCaptor
                .forClass(List.class);

        verify(getSpecificationsBuilder(), atLeastOnce())
                .buildSpecification(searchCriteriaCaptor.capture());

        Specification<EntityType> specification = getLastCapturedSpecification();

        assertNotNull(specification, expectedSearchCriteriaList.toString());
        assertEquals(expectedSearchCriteriaList, searchCriteriaCaptor.getValue());
    }


    @Test
    public void testGetAllFilteringWithWrongValue() throws Exception {
        mockPage();

        mockMvc.perform(get(getControllerPath())
                        .param("search","wrongValue"))
                .andExpect(status().isOk());

        assertNull(getLastCapturedSpecification());
    }

    @Test
    public void testSuccessGetRecord() throws Exception {
        EntityType entity = getNewEntity();

        mockServiceMethodGetOneById(entity);

        ApiResponseType expectedResponse = convertToResponse(entity);

        mockMvc.perform(get(getControllerPath() + "/{id}", entity.getId())
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(expectedResponse)));
    }

    @Test
    public void testNotFoundGetRecord() throws Exception {
        EntityType entity = getNewEntity();
        entity.setId(1L);

        mockServiceMethodGetOneById(entity);

        mockMvc.perform(get(getControllerPath() + "/{id}", 33)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateRecord() throws Exception {

        captureEntityFromMock(resultCaptor, when(getService().create(any(getEntityClass()))));

        when(getConverter().requestToEntity(any(getRequestClass()))).thenReturn(getNewEntity());

        ResultActions resultActions = mockMvc.perform(post(getControllerPath())
                        .content(toJson(getNewRequest()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());

        assertNotNull(resultCaptor.getResult());

        ApiResponseType expectedResponse = convertToResponse(resultCaptor.getResult());
        resultActions.andExpect(content().json(toJson(expectedResponse)));
    }

    @Test
    public void testUpdateRecord() throws Exception{
        EntityType entity = getNewEntity();

        EntityType entityWithNewValues = getNewEntity();
        entityWithNewValues.setId(entity.getId());

        ApiRequestType request = convertToRequest(entityWithNewValues);

        mockServiceMethodGetOneById(entity);
        captureEntityFromMock(resultCaptor, when(getService().update(any(getEntityClass()))));

        ResultActions putRequest = mockMvc.perform(
                put(getControllerPath() + "/{id}", entity.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(toJson(request)))
                .andExpect(status().isOk());

        EntityType updatedEntity = resultCaptor.getResult();

        ApiResponseType expectedResponse = convertToResponse(updatedEntity);
        putRequest.andExpect(content().json(toJson(expectedResponse)));

        List<Function<EntityType, Object>> getters = getUpdatableFieldGetters();

        for (Function<EntityType, Object> getter : getters) {
            assertEquals(getter.apply(entityWithNewValues), getter.apply(updatedEntity));
        }
    }

    @Test
    public void testPatchRecord() {
        assumeTrue(isPatchingAllowed(), "testPatchRecord was skipped, because it is not allowed");
    }

    @Test
    public void testDeleteRecord() throws Exception {
        EntityType entity = getNewEntity();

        mockServiceMethodGetOneById(entity);

        mockMvc.perform(delete(getControllerPath() + "/{id}", entity.getId()))
                .andExpect(status().isNoContent());

        verify(getService(), times(1)).delete(eq(entity));
    }

    protected Pageable getLastCapturedPageRequest() {
        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);

        verify(getService(), atLeastOnce()).get(any(), captor.capture());

        return captor.getValue();
    }

    @SuppressWarnings("unchecked")
    protected Specification<EntityType> getLastCapturedSpecification() {
        ArgumentCaptor<Specification<EntityType>> captor = ArgumentCaptor
                .forClass(Specification.class);

        verify(getService(), atLeastOnce()).get(captor.capture(), any(Pageable.class));

        return captor.getValue();
    }

    protected void mockPage() {
        mockPage(new ArrayList<>());
    }

    @SuppressWarnings("unchecked")
    protected Page<EntityType> mockPage(List<EntityType> entities) {
        Page<EntityType> page = mock(Page.class);

        when(page.getContent()).thenReturn(entities);
        when(page.getTotalElements()).thenReturn(10L);
        when(page.getNumber()).thenReturn(1);
        when(page.getSize()).thenReturn(5);

        when(getService().get(any(), any(Pageable.class))).thenReturn(page);
        return page;
    }

    protected void mockServiceMethodGetOneById(EntityType entity) {
        when(getService().getOne(entity.getId())).thenReturn(entity);
    }

    /**
     * To use when mocked method return {@link EntityType}
     */
    protected <T> void captureEntityFromMock(ResultCaptor<EntityType> resultCaptor,
                                             OngoingStubbing<T> ongoingStubbing) {
        ongoingStubbing.thenAnswer(
                invocation -> {
                    EntityType entity = invocation.getArgument(0, getEntityClass());
                    resultCaptor.setResult(entity);
                    return entity;
                }
        );
    }

}
