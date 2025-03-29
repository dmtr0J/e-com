package com.practice.backend.dto;

import com.practice.backend.api.v1.common.ApiRequest;
import com.practice.backend.model.entity.Category;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CategoryRequest implements ApiRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @Nullable
    private Category parent;
}
