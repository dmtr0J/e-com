package com.practice.backend.dto;

import com.practice.backend.api.v1.common.ApiResponse;
import com.practice.backend.model.entity.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ProductResponse implements ApiResponse {

    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private Category category;

    private String imageName;

}
