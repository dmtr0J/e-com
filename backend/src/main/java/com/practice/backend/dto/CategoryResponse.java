package com.practice.backend.dto;

import com.practice.backend.api.v1.common.ApiResponse;
import com.practice.backend.model.entity.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CategoryResponse implements ApiResponse {

    private Long id;
    private String name;
    private Category parent;

}


