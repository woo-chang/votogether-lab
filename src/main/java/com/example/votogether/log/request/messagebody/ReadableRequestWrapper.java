package com.example.votogether.log.request.messagebody;

import static java.nio.charset.StandardCharsets.UTF_8;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

//HttpServletRequestWrapper를 상속받음으로 HttpServletRequest 객체를 래핑하여 HTTP 요청 입력 스트림을 재사용할 수 있도록 한다.
public class ReadableRequestWrapper extends HttpServletRequestWrapper {

    //요청 본문의 문자 인코딩을 저장하는 객체
    private final Charset encoding;
    //요청 본문의 원시 데이터를 배열로 저장한다.
    private final byte[] rawData;

    public ReadableRequestWrapper(HttpServletRequest request) {
        super(request);
        String charEncoding = request.getCharacterEncoding();
        this.encoding = getEncoding(charEncoding);
        try {
            //원래 요청의 입력 스트림을 가져온다.
            InputStream is = request.getInputStream();
            //입력 스트림의 모든 바이트를 읽어 필드로 저장한다.
            this.rawData = is.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Charset getEncoding(String charEncoding) {
        if (StringUtils.isBlank(charEncoding)) {
            return UTF_8;
        }
        return Charset.forName(charEncoding);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        //저장된 바이트를 사용하여 입력 스트림을 생성한다.
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.rawData);

        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                throw new UnsupportedOperationException("[ReadableRequestWrapper] isFinished() not supported");
            }

            @Override
            public boolean isReady() {
                throw new UnsupportedOperationException("[ReadableRequestWrapper] isReady() not supported");
            }

            @Override
            public void setReadListener(ReadListener listener) {
            }

            public int read() {
                return byteArrayInputStream.read();
            }
        };
    }

    @Override
    public BufferedReader getReader() {
        try {
            return new BufferedReader(new InputStreamReader(this.getInputStream(), this.encoding));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
