package com.example.manageasset.domain.shared.exceptions;

public class InternalServerException extends Exception {
    public InternalServerException(String s) {
        super(s);
    }

    public InternalServerException(String s, Throwable throwable) {
        super(s, throwable);
    }
}

