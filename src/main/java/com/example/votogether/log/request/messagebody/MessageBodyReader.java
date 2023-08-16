package com.example.votogether.log.request.messagebody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.util.stream.Collectors;
import org.springframework.web.util.ContentCachingResponseWrapper;

public class MessageBodyReader {

    @NotNull
    public static String readBody(HttpServletRequest request) {
        if (request.getContentLength() == 0) {
            return "";
        }
        try {
            //getReader는 메서드 본문을 읽기 위한 BufferedReader 객체를 반환한다.
            //lines 메서드는 한 줄씩 읽어 스트림을 생성한다.
            return request.getReader().lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    public static String readBody(HttpServletResponse response) {
        return new String(((ContentCachingResponseWrapper) response).getContentAsByteArray());
    }
}
