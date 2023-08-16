package com.example.votogether.log.query;

import java.lang.reflect.Method;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.web.context.request.RequestContextHolder;

@RequiredArgsConstructor
public class PreparedStatementMethodInterceptor implements MethodInterceptor {

    private final QueryCounter queryCounter;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (isExecuteQuery(invocation.getMethod()) && isInRequestScope()) {
            queryCounter.increase();
        }
        return invocation.proceed();
    }

    private boolean isExecuteQuery(Method method) {
        //메서드가 아래와 같은지 확인한다.
        String methodName = method.getName();
        return methodName.equals("executeQuery") || methodName.equals("execute") || methodName.equals("executeUpdate");
    }

    private boolean isInRequestScope() {
        //이 값이 null이 아니라면 Request Scope 내에 있음을 의미한다.
        return Objects.nonNull(RequestContextHolder.getRequestAttributes());
    }
}
