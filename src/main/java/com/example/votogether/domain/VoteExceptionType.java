package com.example.votogether.domain;

import com.example.votogether.exception.ExceptionType;
import lombok.Getter;

@Getter
public enum VoteExceptionType implements ExceptionType {

    INVALID_VOTE(1000, "유효하지 않은 투표입니다."),
    ;

    private final int code;
    private final String message;

    VoteExceptionType(final int code, final String message) {
        this.code = code;
        this.message = message;
    }

}
