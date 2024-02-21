package com.softdignitas.ddd.web.rest.lazyload;

import org.springframework.data.domain.Sort;

public enum SortOrder {
    ASC(1),
    DESC(-1);

    private final int value;

    SortOrder(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SortOrder fromValue(int value) {
        for (SortOrder order : SortOrder.values()) {
            if (order.value == value) {
                return order;
            }
        }
        throw new IllegalArgumentException("Invalid sortOrder value: " + value);
    }

    public Sort.Direction getSortDirection() {
        return value == -1 ? Sort.Direction.DESC : Sort.Direction.ASC;
    }
}
