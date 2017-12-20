package org.ametyst.metrics;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
@Aspect
@Component
public class Aspekt {
    @Pointcut("within(@EnableLogging *)")
    public void methodsInsideWatchedClasses() {}

    @Around("methodsInsideWatchedClasses()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
//        long start = System.currentTimeMillis();

        Object proceed = joinPoint.proceed();

//        long executionTime = System.currentTimeMillis() - start;
//
//        System.out.println(joinPoint.getSignature() + " executed in " + executionTime + "ms");
        return proceed;
    }



//    @Before("")
//    public void a() {
//
//    }
//
//    @After("")
//    public void after() {
//
//    }
//
//    @AfterThrowing("")
//    public void afterThrowing() {
//
//    }
//
//    @AfterReturning("")
//    public void afterReturning() {
//
//    }
}