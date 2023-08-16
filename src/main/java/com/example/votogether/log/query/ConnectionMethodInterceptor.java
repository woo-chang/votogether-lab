package com.example.votogether.log.query;

import java.sql.PreparedStatement;
import lombok.RequiredArgsConstructor;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;

//Spring AOP 인터페이스를 구현한 인터셉터 클래스
@RequiredArgsConstructor
public class ConnectionMethodInterceptor implements MethodInterceptor {

    private final QueryCounter queryCounter;

    //대상 객체의 메서드가 호출될 때 AOP 프레임워크에 의해 자동 호출
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        //대상 객체의 원래 메서드를 실행하고, 그 결과를 proceed에 저장한다.
        Object proceed = invocation.proceed();
        if (proceed instanceof PreparedStatement ps) {
            //ps에 대한 프록시 생성을 위한 ProxyFactory 객체 생성
            ProxyFactory proxyFactory = new ProxyFactory(ps);
            //Advice를 등록한다.
            //Advice는 특정 지점에 실행되어야 할 행동을 정의한 것이다.
            proxyFactory.addAdvice(new PreparedStatementMethodInterceptor(queryCounter));
            //프록시 객체를 생성하여 반환한다.
            return proxyFactory.getProxy();
        }
        return proceed;
    }
}
