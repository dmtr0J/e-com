package com.practice.backend.dto;

import com.practice.backend.api.v1.common.ApiRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductRequest implements ApiRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    private BigDecimal price;

    @NotBlank
    private String categoryName;

    @NotBlank
    private String imageName;
}
