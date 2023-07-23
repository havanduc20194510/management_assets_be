package com.example.manageasset.domain.asset.models;

import com.example.manageasset.domain.shared.exceptions.InvalidDataException;
import com.example.manageasset.infrastructure.shared.jsons.MyObjectMapper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class State {
    private final int value;

    public static final int READY_TYPE = 0;
    public static final int NOT_READY_TYPE = 1;
    public static final int MAINTENANCE_TYPE = 2;

    private static final Set<Integer> values = new HashSet<>(
            Arrays.asList(
                    READY_TYPE,
                    MAINTENANCE_TYPE,
                    NOT_READY_TYPE
            )
    );

    public State(int value) {
        this.value = value;
        validate();
    }

    private void validate() {
        if (!values.contains(value))
            throw new InvalidDataException("State must be in " + MyObjectMapper.serialize(values));
    }

    public int asInt() {
        return value;
    }

    public static final State READY = new State(READY_TYPE);
    public static final State NOT_READY = new State(NOT_READY_TYPE);
    public static final State MAINTENANCE = new State(MAINTENANCE_TYPE);
}
