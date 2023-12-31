package com.example.votogether.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private final ExceptionType exceptionType;

    public BaseException(final ExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

}
