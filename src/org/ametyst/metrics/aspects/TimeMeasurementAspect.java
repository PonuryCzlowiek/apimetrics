package org.ametyst.metrics.aspects;

import org.ametyst.metrics.measurement.ExecutionTimeMeasurement;
import org.ametyst.metrics.measurement.MeasurementType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
@Aspect
@Component
public class TimeMeasurementAspect extends AspectPublishingEvents {
    @Pointcut("within(@EnableLogging *)")
    public void methodsInsideWatchedClasses() {}

    @Pointcut("within(@org.springframework.scheduling.annotation.Scheduled *)")
    public void scheduledMethod() {}

    @Around("methodsInsideWatchedClasses()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        System.out.println(joinPoint.getSignature() + " executed in " + executionTime + "ms");
        return proceed;
    }

    @Around("scheduledMethod()")
    public Object logScheduledMethodExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        ExecutionTimeMeasurement executionTimeMeasurement = new ExecutionTimeMeasurement(joinPoint.getSignature().getDeclaringType().getName(),
                                                                                         joinPoint.getSignature().getName(),
                                                                                         executionTime);
        publishEvent(MeasurementType.SCHEDULED, executionTimeMeasurement);
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