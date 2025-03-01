package com.practice.backend.api.v1.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<EntityType> {

    private long totalElements;
    private long index;
    private long size;
    private List<EntityType> items;

}
