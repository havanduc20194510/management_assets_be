package com.example.manageasset.domain.shared.exceptions;

public class UnauthorizedException extends Exception {
    public UnauthorizedException(String s) {
        super(s);
    }

    public UnauthorizedException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
