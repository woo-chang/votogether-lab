package com.example.votogether.log.context;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public interface LogId {

    static LogId fromRequest(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return AnonymousLogId.randomId();
        }
        return new AuthenticatedLogId(session.getAttribute("JSESSIONID"));
    }

    String logId();
}
