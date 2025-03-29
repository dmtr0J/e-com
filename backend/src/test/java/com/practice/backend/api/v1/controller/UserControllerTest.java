package com.practice.backend.api.v1.controller;

import com.practice.backend.api.v1.ApiConstants;
import com.practice.backend.api.v1.controller.common.AbstractControllerTest;
import com.practice.backend.builder.UserBuilder;
import com.practice.backend.converter.Converter;
import com.practice.backend.converter.UserConverter;
import com.practice.backend.dto.UserRequest;
import com.practice.backend.dto.UserResponse;
import com.practice.backend.filtering.UserSpecificationBuilder;
import com.practice.backend.filtering.common.FilterableProperty;
import com.practice.backend.filtering.common.FilteringOperation;
import com.practice.backend.filtering.common.SearchCriteria;
import com.practice.backend.filtering.specification.EntityFilterSpecificationBuilder;
import com.practice.backend.filtering.specification.EqualingSpecificationBuilder;
import com.practice.backend.filtering.specification.LocalDateComparisonSpecificationBuilder;
import com.practice.backend.filtering.specification.StringComparisonSpecificationBuilder;
import com.practice.backend.model.entity.User;
import com.practice.backend.service.AbstractService;
import com.practice.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Sort;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@WebMvcTest(UserController.class)
@WithMockUser(roles = {"ADMIN"})
@MockitoBean(types = {
        UserService.class,
        UserConverter.class,
        UserSpecificationBuilder.class,
})
public class UserControllerTest extends AbstractControllerTest<
        User, UserRequest, UserResponse> {

    @Autowired
    private UserService userService;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private UserSpecificationBuilder userSpecificationBuilder;

    @Override
    protected boolean isPatchingAllowed() {
        return false;
    }

    @Override
    protected Converter<User, UserRequest, UserResponse> getConverter() {
        return this.userConverter;
    }

    @Override
    protected AbstractService<User> getService() {
        return this.userService;
    }

    @Override
    protected EntityFilterSpecificationBuilder<User> getSpecificationsBuilder() {
        return this.userSpecificationBuilder;
    }

    @Override
    protected UserResponse convertToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .phone(user.getPhone())
                .firstName(user.getFirstName())
                .middleName(user.getMiddleName())
                .lastName(user.getLastName())
                .birthDate(user.getBirthDate())
                .orders(user.getOrders())
                .build();
    }

    @Override
    protected UserRequest convertToRequest(User user) {
        return UserRequest.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .phone(user.getPhone())
                .firstName(user.getFirstName())
                .middleName(user.getMiddleName())
                .lastName(user.getLastName())
                .birthDate(user.getBirthDate())
                .build();
    }

    @Override
    protected User getNewEntity() {
        return UserBuilder.start().build();
    }

    @Override
    protected UserRequest getNewRequest() {
        return convertToRequest(getNewEntity());
    }

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    protected Class<UserRequest> getRequestClass() {
        return UserRequest.class;
    }

    @Override
    protected String getControllerPath() {
        return ApiConstants.USER_PATH;
    }

    @Override
    protected List<Function<User, Object>> getUpdatableFieldGetters() {
        return List.of(
                User::getEmail,
                User::getPassword,
                User::getRole,
                User::getPhone,
                User::getFirstName,
                User::getMiddleName,
                User::getLastName,
                User::getBirthDate
        );
    }

    @Override
    protected Map<String, Sort> getSortingTestParameters() {
        HashMap<String, Sort> parameters = new HashMap<>();

        parameters.put("email", Sort.by(Sort.Direction.ASC, "email"));
        parameters.put("email" + ",DESC", Sort.by(Sort.Direction.DESC, "email"));

        parameters.put("role", Sort.by(Sort.Direction.ASC, "role"));
        parameters.put("role" + ",DESC", Sort.by(Sort.Direction.DESC, "role"));

        parameters.put("phone", Sort.by(Sort.Direction.ASC, "phone"));
        parameters.put("phone" + ",DESC", Sort.by(Sort.Direction.DESC, "phone"));

        parameters.put("firstName", Sort.by(Sort.Direction.ASC, "firstName"));
        parameters.put("firstName" + ",DESC", Sort.by(Sort.Direction.DESC, "firstName"));

        parameters.put("middleName", Sort.by(Sort.Direction.ASC, "middleName"));
        parameters.put("middleName" + ",DESC", Sort.by(Sort.Direction.DESC, "middleName"));

        parameters.put("lastName", Sort.by(Sort.Direction.ASC, "lastName"));
        parameters.put("lastName" + ",DESC", Sort.by(Sort.Direction.DESC, "lastName"));

        parameters.put("birthDate", Sort.by(Sort.Direction.ASC, "birthDate"));
        parameters.put("birthDate" + ",DESC", Sort.by(Sort.Direction.DESC, "birthDate"));

        return parameters;
    }

    protected Map<List<String>, List<SearchCriteria>> getFilteringTestParameters() {
        Map<List<String>, List<SearchCriteria>> filteringTestParameters = new HashMap<>();

        filteringTestParameters.put(
                List.of("email=test1@test.com", "email!=test2@test.com", "email:test3@test.com"),
                List.of(
                        new SearchCriteria("email", FilteringOperation.EQUAL, "test1@test.com"),
                        new SearchCriteria("email", FilteringOperation.NOT_EQUAL, "test2@test.com"),
                        new SearchCriteria("email", FilteringOperation.CONTAIN, "test3@test.com")
                )
        );
        filteringTestParameters.put(
                List.of("firstName=John", "firstName!=Doe", "firstName:Jo"),
                List.of(
                        new SearchCriteria("firstName", FilteringOperation.EQUAL, "John"),
                        new SearchCriteria("firstName", FilteringOperation.NOT_EQUAL, "Doe"),
                        new SearchCriteria("firstName", FilteringOperation.CONTAIN, "Jo")
                )
        );
        filteringTestParameters.put(
                List.of("middleName=Michael", "middleName!=Chris", "middleName:Mic"),
                List.of(
                        new SearchCriteria("middleName", FilteringOperation.EQUAL, "Michael"),
                        new SearchCriteria("middleName", FilteringOperation.NOT_EQUAL, "Chris"),
                        new SearchCriteria("middleName", FilteringOperation.CONTAIN, "Mic")
                )
        );
        filteringTestParameters.put(
                List.of("lastName=Smith", "lastName!=Johnson", "lastName:Smi"),
                List.of(
                        new SearchCriteria("lastName", FilteringOperation.EQUAL, "Smith"),
                        new SearchCriteria("lastName", FilteringOperation.NOT_EQUAL, "Johnson"),
                        new SearchCriteria("lastName", FilteringOperation.CONTAIN, "Smi")
                )
        );
        filteringTestParameters.put(
                List.of("phone=1234567890", "phone!=0987654321"),
                List.of(
                        new SearchCriteria("phone", FilteringOperation.EQUAL, "1234567890"),
                        new SearchCriteria("phone", FilteringOperation.NOT_EQUAL, "0987654321")
                )
        );
        filteringTestParameters.put(
                List.of(
                        "birthDate=2000-01-01",
                        "birthDate!=1990-05-20",
                        "birthDate>1995-06-15",
                        "birthDate<2010-12-31",
                        "birthDate>=2005-08-10",
                        "birthDate<=2015-07-25"
                ),
                List.of(
                        new SearchCriteria("birthDate", FilteringOperation.EQUAL, LocalDate.of(2000, 1, 1)),
                        new SearchCriteria("birthDate", FilteringOperation.NOT_EQUAL, LocalDate.of(1990, 5, 20)),
                        new SearchCriteria("birthDate", FilteringOperation.GREATER_THEN, LocalDate.of(1995, 6, 15)),
                        new SearchCriteria("birthDate", FilteringOperation.LESS_THEN, LocalDate.of(2010, 12, 31)),
                        new SearchCriteria("birthDate", FilteringOperation.GREATER_OR_EQUAL, LocalDate.of(2005, 8, 10)),
                        new SearchCriteria("birthDate", FilteringOperation.LESS_OR_EQUAL, LocalDate.of(2015, 7, 25))
                )
        );
        return filteringTestParameters;
    }

    @Override
    protected List<FilterableProperty<User>> getFilterableProperties() {
        return List.of(
                new FilterableProperty<>("email", String.class,
                        StringComparisonSpecificationBuilder.SUPPORTED_OPERATORS,
                        new StringComparisonSpecificationBuilder<>()),

                new FilterableProperty<>("firstName", String.class,
                        StringComparisonSpecificationBuilder.SUPPORTED_OPERATORS,
                        new StringComparisonSpecificationBuilder<>()),

                new FilterableProperty<>("middleName", String.class,
                        StringComparisonSpecificationBuilder.SUPPORTED_OPERATORS,
                        new StringComparisonSpecificationBuilder<>()),

                new FilterableProperty<>("lastName", String.class,
                        StringComparisonSpecificationBuilder.SUPPORTED_OPERATORS,
                        new StringComparisonSpecificationBuilder<>()),

                new FilterableProperty<>("phone", String.class,
                        EqualingSpecificationBuilder.SUPPORTED_OPERATORS,
                        new EqualingSpecificationBuilder<>()),

                new FilterableProperty<>("birthDate", LocalDate.class,
                        LocalDateComparisonSpecificationBuilder.SUPPORTED_OPERATORS,
                        new LocalDateComparisonSpecificationBuilder<>())
        );
    }

}
