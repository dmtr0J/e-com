package com.practice.backend.dao;

import com.practice.backend.filtering.common.FilteringOperation;
import com.practice.backend.filtering.common.SearchCriteria;
import com.practice.backend.filtering.specification.EqualingSpecification;
import com.practice.backend.model.Identifiable;
import com.practice.backend.repository.BaseIdentifiableRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public abstract class AbstractIdentifiableDaoTest<EntityType extends Identifiable, DaoType extends AbstractIdentifiableDao<EntityType>, RepositoryType extends BaseIdentifiableRepository<EntityType>> {
    @Mock
    protected DaoType daoService;

    protected EntityType entity;

    public abstract EntityType createEntity();

    public abstract DaoType createDao();

    public abstract RepositoryType createRepository();

    @BeforeEach
    public void setup() throws Exception {
        MockitoAnnotations.openMocks(this).close();
        daoService = createDao();
        entity = createEntity();
        entity.setId(1L);
    }

    @Test
    public void shouldReturnPageableRecords() {
        given(createRepository().findAll(any(Pageable.class))).willReturn(new PageImpl<>(Collections.singletonList(entity)));

        PageRequest request = PageRequest.of(0, 1, Sort.unsorted());
        daoService.getAll(request);

        verify(createRepository()).findAll(any(Pageable.class));
    }

    @Test
    public void shouldReturnAllSortedRecords() {
        given(createRepository().findAll(any(Sort.class))).willReturn(Collections.singletonList(entity));

        daoService.getAll(Sort.unsorted());

        verify(createRepository()).findAll(any(Sort.class));
    }

    @Test
    public void shouldReturnAllFilteredSortedRecords() {
        given(createRepository().findAll(any(Specification.class), any(Sort.class))).willReturn(Collections.singletonList(entity));

        Specification<EntityType> specification = new EqualingSpecification<>(new SearchCriteria("name", FilteringOperation.EQUAL, "entity"));
        daoService.getAll(specification, Sort.unsorted());

        verify(createRepository()).findAll(any(Specification.class), any(Sort.class));
    }

    @Test
    public void shouldReturnPageableFilteredRecords() {
        given(createRepository().findAll(any(Specification.class), any(Pageable.class))).willReturn(new PageImpl<>(Collections.singletonList(entity)));

        Specification<EntityType> specification = new EqualingSpecification<>(new SearchCriteria("name", FilteringOperation.EQUAL, "entity"));
        PageRequest request = PageRequest.of(0, 1, Sort.unsorted());
        daoService.getAll(specification, request);

        verify(createRepository()).findAll(any(Specification.class), any(Pageable.class));
    }
}