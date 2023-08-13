package com.example.votogether;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LogController {

    private final Logger log = LogManager.getLogger(LogController.class);
    private final LogService logService;

    @GetMapping("/log")
    public String log() {
        log.trace("trace-log");
        log.debug("debug-log");
        log.info("info-log");
        log.warn("warn-log");
        log.error("error-log");
        return "log";
    }
}
