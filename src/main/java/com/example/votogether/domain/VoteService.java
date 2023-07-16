package com.example.votogether.domain;

import com.example.votogether.exception.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class VoteService {

    public String vote() {
        final int type = 1;
        if (type == 1) {
            throw new BadRequestException(VoteExceptionType.INVALID_VOTE);
        }
        return "vote";
    }

    public String vote2() {
        return "vote2";
    }

    public String vote3() {
        return "vote3";
    }

    public String vote4() {
        return "vote4";
    }

}
