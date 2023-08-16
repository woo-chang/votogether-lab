package com.example.votogether.log.context;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public interface LogId {

    static LogId fromRequest(HttpServletRequest request) {
        //서버는 클라이언트 요청이 들어올 때 HTTP 요청 헤더에 세션 ID가 있는지 확인
        HttpSession session = request.getSession(false);
        //세션 ID가 없거나, 세션 ID에 해당하는 세션 정보가 서버에 없다면 로그인하지 않은 회원이기에 랜덤값 생성
        if (session == null) {
            return AnonymousLogId.randomId();
        }
        //존재한다면 로그인한 회원이기 때문에 멤버 ID 추출
        return new AuthenticatedLogId(session.getAttribute("JSESSIONID"));
    }

    String logId();
}
