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

}
