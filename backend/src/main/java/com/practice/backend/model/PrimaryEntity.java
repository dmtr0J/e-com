package com.practice.backend.model;

public interface PrimaryEntity<T> {

    T getId();

    void setId(T id);
}
