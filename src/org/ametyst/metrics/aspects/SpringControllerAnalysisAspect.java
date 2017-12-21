package org.ametyst.metrics.aspects;

import eu.bitwalker.useragentutils.UserAgent;
import org.ametyst.metrics.measurement.ClientMeasurement;
import org.ametyst.metrics.measurement.ExceptionMeasurement;
import org.ametyst.metrics.measurement.ExecutionTimeMeasurement;
import org.ametyst.metrics.measurement.MeasurementType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Aspect
@Component
@RequiredTypes(value = {"org.springframework.stereotype.Controller",
        "org.springframework.web.bind.annotation.RequestMapping"})
public class SpringControllerAnalysisAspect extends AspectPublishingEvents {
    // any method within controller or its specialization
    @Pointcut("within(@(@org.springframework.stereotype.Controller *) *)")
    public void controllers(){}

    // all methods with @RequestMapping or its specialization
    @Pointcut("execution(@(@org.springframework.web.bind.annotation.RequestMapping *) * *(..))")
    public void methodsWithRequestMapping(){}

    @Around(value = "controllers() && methodsWithRequestMapping()")
    public Object measureControllerMethodExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable{
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        ExecutionTimeMeasurement executionTimeMeasurement = new ExecutionTimeMeasurement(joinPoint.getSignature().getDeclaringType().getName(),
                                                                                         joinPoint.getSignature().getName(),
                                                                                         executionTime);
        publishEvent(MeasurementType.REQUEST, executionTimeMeasurement);
        return proceed;
    }

    @Before(value = "methodsWithRequestMapping()")
    public void logRequestsPerClient(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String requestUrl = request.getScheme() + "://" + request.getServerName()
                            + ":" + request.getServerPort() + request.getContextPath() + request.getRequestURI();

        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        ClientMeasurement clientMeasurement = new ClientMeasurement(request.getRemoteAddr(),
                                                                    requestUrl,
                                                                    Optional.ofNullable(request.getQueryString()).orElse(""),
                                                                    userAgent.getOperatingSystem().getName(),
                                                                    userAgent.getOperatingSystem().getDeviceType().getName(),
                                                                    userAgent.getBrowser().getName(),
                                                                    userAgent.getBrowserVersion().getVersion());
        publishEvent(MeasurementType.CLIENT, clientMeasurement);
    }

    @AfterThrowing(value = "methodsWithRequestMapping()", throwing = "exceptionalReturnValue")
    public void logExceptionalResponses(JoinPoint joinPoint, Exception exceptionalReturnValue) {
        ExceptionMeasurement exceptionMeasurement = new ExceptionMeasurement(joinPoint.getSignature().getDeclaringType().getName(),
                                                                             joinPoint.getSignature().getName(),
                                                                             exceptionalReturnValue.getClass().getName());
        publishEvent(MeasurementType.EXCEPTION, exceptionMeasurement);
    }
}
