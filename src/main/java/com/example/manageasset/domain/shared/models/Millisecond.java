package com.example.manageasset.domain.shared.models;

import com.example.manageasset.domain.shared.exceptions.InvalidDataException;

import java.util.Objects;

public class Millisecond {
    private final long value;

    public Millisecond(long value) {
        this.value = value;
        validate();
    }

    public static Millisecond now() {
        return new Millisecond(System.currentTimeMillis());
    }

    private void validate() {
        if (value < 0) throw new InvalidDataException("Millisecond must be >= 0");
    }

    public long asLong() {
        return value;
    }

    public boolean isAfter(Millisecond that) {
        return value > that.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Millisecond that = (Millisecond) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
