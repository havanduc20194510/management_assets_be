package com.example.manageasset.domain.shared.models;

import com.example.manageasset.domain.shared.exceptions.InvalidDataException;
import com.example.manageasset.infrastructure.shared.jsons.MyObjectMapper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Status {
    private final int value;

    public static final int INPROGRESS_TYPE = 0;
    public static final int REJECT_TYPE = 1;
    public static final int APPROVED_TYPE = 2;

    private static final Set<Integer> values = new HashSet<>(
            Arrays.asList(
                    INPROGRESS_TYPE,
                    REJECT_TYPE,
                    APPROVED_TYPE
            )
    );

    public Status(int value) {
        this.value = value;
        validate();
    }

    private void validate() {
        if (!values.contains(value))
            throw new InvalidDataException("Status must be in " + MyObjectMapper.serialize(values));
    }

    public int asInt() {
        return value;
    }

    public static final Status INPROGRESS = new Status(INPROGRESS_TYPE);
    public static final Status REJECT = new Status(REJECT_TYPE);
    public static final Status APPROVED = new Status(APPROVED_TYPE);
}
