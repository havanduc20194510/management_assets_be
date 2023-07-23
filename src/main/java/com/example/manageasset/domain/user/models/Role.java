package com.example.manageasset.domain.user.models;

import java.util.Objects;

public enum Role {
    ADMIN,
    STAFF,
    USER;

    public static Role fromValue(String value) {
        if(value != null) {
            for (Role role : values()) {
                if (Objects.equals(role.name(), value)) {
                    return role;
                }
            }
            throw new IllegalArgumentException("Cannot create enum from " + value + " value!");
        }
        else throw new IllegalArgumentException("Value cannot be null or empty!");

    }
}
