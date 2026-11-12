package com.site.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceMonitoringAspect{
    @Around("execution(* com.site.service.AccountService.*(..))")
    public Object monitorExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable{
        long startTime = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().toShortString();
        try{
            return joinPoint.proceed();
        } finally{
            long executionTime = System.currentTimeMillis() - startTime;
            System.out.println("[PERFORMANCE - @Around] " + methodName + " executed in " + executionTime + " ms");
        }
    }
}
