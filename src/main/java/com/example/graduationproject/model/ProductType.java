package com.example.graduationproject.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ProductType {
    GLUTEN_FREE, NOT_GLUTEN_FREE, UNKNOWN;

    @JsonCreator
    public static ProductType fromString(String key) {
        return ProductType.valueOf(key.toUpperCase());
    }
}
