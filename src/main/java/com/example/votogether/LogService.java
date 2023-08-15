package com.example.votogether;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class LogService {

    @Transactional(readOnly = true)
    public String log() {
        System.out.println("hello service");
        log.trace("trace-log");
        log.debug("debug-log");
        log.info("info-log");
        log.warn("warn-log");
        log.error("error-log");
        return "log";
    }
}
