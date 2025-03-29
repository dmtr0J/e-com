package com.practice.backend.filtering.common;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class SearchCriteria{

     private String key;
     private FilteringOperation operation;
     private Object value;

}