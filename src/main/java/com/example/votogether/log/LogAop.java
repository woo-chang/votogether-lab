package com.example.votogether.log;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LogAop {

    private final Logger logger;

    //포인트컷은 Advice의 적용대상을 의미한다.
    //@RestController 어노테이션이 붙은 클래스에서 실행되는 메서드를 포인트 컷으로 정의한다.
    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void restControllerAnnotatedClass() {
    }

    //@Service 어노테이션이 붙은 클래스에서 실행되는 메서드를 포인트 컷으로 정의한다.
    @Pointcut("@within(org.springframework.stereotype.Service)")
    public void serviceAnnotatedClass() {
    }

    //특정 패키지 하위에 있는 @Repository로 끝나는 인터페이스/클래스의 모든 메서드를 포인트 컷으로 정의한다.
    @Pointcut("execution(* com.celuveat..*Repository+.*(..))")
    public void repositoryClass() {
    }

    @Around("restControllerAnnotatedClass() || serviceAnnotatedClass() || repositoryClass()")
    //JoinPoint는 가로챌 수 있는 어느 시점을 의미한다.
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        if (isNotRequestScope()) {
            return joinPoint.proceed();
        }
        String methodName = getMethodName(joinPoint);
        String className = getClassSimpleName(joinPoint);
        logger.methodCall(className, methodName);
        try {
            Object result = joinPoint.proceed();
            logger.methodReturn(className, methodName);
            return result;
        } catch (Throwable e) {
            logger.throwException(className, methodName, e);
            throw e;
        }
    }

    private boolean isNotRequestScope() {
        return Objects.isNull(RequestContextHolder.getRequestAttributes());
    }

    private String getClassSimpleName(ProceedingJoinPoint joinPoint) {
        Class<?> clazz = joinPoint.getTarget().getClass();
        String className = clazz.getSimpleName();
        if (className.contains("Proxy")) {
            className = clazz.getInterfaces()[0].getSimpleName();
        }
        return className;
    }

    private String getMethodName(ProceedingJoinPoint joinPoint) {
        return joinPoint.getSignature().getName();
    }
}
