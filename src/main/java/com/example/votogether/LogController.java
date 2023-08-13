package com.example.votogether;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LogController {

    private final LogService logService;

    @GetMapping("/log")
    public String log() {
        return logService.log();
    }
}
