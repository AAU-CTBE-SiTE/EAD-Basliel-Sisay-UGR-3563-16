package com.site.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import java.util.Arrays;
@Aspect
@Component
public class LoggingAspect{
    @Pointcut("execution(* com.site.service.AccountService.*(..))")
    public void accountServiceMethods() {}
    @Before("accountServiceMethods()")
    public void logBefore(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();
        System.out.println("[LOGGING - @Before] Entering: " + methodName + " | Arguments: " + Arrays.toString(args));
    }
    @AfterReturning(pointcut = "accountServiceMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().toShortString();
        System.out.println("[LOGGING - @AfterReturning] Exited: " + methodName + " | Returned: " + result);
    }
    @AfterThrowing(pointcut = "accountServiceMethods()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception){
        String methodName = joinPoint.getSignature().toShortString();
        System.out.println("[LOGGING - @AfterThrowing] Exception in: " + methodName + " | Message: " + exception.getMessage());
    }
}
