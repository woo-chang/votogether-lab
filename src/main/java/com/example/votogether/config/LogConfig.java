package com.example.votogether.config;

import com.example.votogether.log.request.RequestLogInterceptor;
import com.example.votogether.log.request.messagebody.RequestResponseChangeFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class LogConfig implements WebMvcConfigurer {

    private final RequestLogInterceptor requestLogInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestLogInterceptor)
                .addPathPatterns("/**")
                .order(1);
    }

    @Bean
    public FilterRegistrationBean<RequestResponseChangeFilter> requestChangeFilter() {
        FilterRegistrationBean<RequestResponseChangeFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RequestResponseChangeFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
