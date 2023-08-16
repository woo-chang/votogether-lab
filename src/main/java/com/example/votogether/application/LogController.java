package com.example.votogether.application;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LogController {

    private final LogService logService;

    @GetMapping("/log")
    public String log() {
        return logService.log();
    }

    @PostMapping("/log")
    public ResponseEntity<LogResponse> logToResponse(
            @RequestParam(required = false) Long id,
            @RequestBody LogRequest request
    ) {
        LogResponse response = logService.logToResponse(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/log/{id}")
    public String pathVariable(@PathVariable Long id) {
        return String.valueOf(id);
    }
}
