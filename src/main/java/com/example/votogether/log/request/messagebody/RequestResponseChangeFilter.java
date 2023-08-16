package com.example.votogether.log.request.messagebody;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.web.util.ContentCachingResponseWrapper;

//Java Servlet Filter 인터페이스를 구현하고 있다.
public class RequestResponseChangeFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        //ContentCachingResponseWrapper는 Spring 프레임워크에서 제공하는 클래스로, HTTP 응답 본문 데이터를 캐시에 저장할 수 있게 한다.
        ContentCachingResponseWrapper wrappingResponse
                = new ContentCachingResponseWrapper((HttpServletResponse) response);
        chain.doFilter(new ReadableRequestWrapper((HttpServletRequest) request), wrappingResponse);
        wrappingResponse.copyBodyToResponse();
    }
}
