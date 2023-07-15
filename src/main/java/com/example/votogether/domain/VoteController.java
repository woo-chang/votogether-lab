package com.example.votogether.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class VoteController {

    private final VoteService voteService;

    @GetMapping("/vote")
    public ResponseEntity<String> vote() {
        final String response = voteService.vote();
        return ResponseEntity.ok(response);
    }

}
