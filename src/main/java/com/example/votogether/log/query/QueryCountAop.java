package com.example.votogether.log.query;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.stereotype.Component;

//해당 클래스가 Aspect로 작동하게 되어, 특정 포인트에 Advice를 실행하도록 한다.
@Aspect
@Component
public class QueryCountAop {

    private final QueryCounter queryCounter;

    public QueryCountAop(QueryCounter queryCounter) {
        this.queryCounter = queryCounter;
    }

    //Around Advice를 정의한다.
    //메서드 호출 전후에 특정 작업을 수행할 수 있도록 한다.
    @Around("execution(* javax.sql.DataSource.getConnection())")
    //getConnection 메서드 호출 전후로 아래의 메서드가 실행된다.
    public Object getConnection(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object connection = proceedingJoinPoint.proceed();
        ProxyFactory proxyFactory = new ProxyFactory(connection);
        proxyFactory.addAdvice(new ConnectionMethodInterceptor(queryCounter));
        return proxyFactory.getProxy();
    }
}
