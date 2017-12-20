package org.ametyst.metrics.aspects;

import org.ametyst.metrics.measurement.ClientMeasurement;
import org.ametyst.metrics.measurement.ExecutionTimeMeasurement;
import org.ametyst.metrics.measurement.MeasurementType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@RequiredTypes(value = {"org.springframework.stereotype.Controller",
        "org.springframework.web.bind.annotation.RequestMapping"})
public class SpringControllerAnalysisAspect extends AspectPublishingEvents {
    // any method within controller or its specialization
    @Pointcut("within(@(@org.springframework.stereotype.Controller *) *)")
    public void restControllers(){}

    // all methods with @RequestMapping or its specialization
    @Pointcut("execution(@(@org.springframework.web.bind.annotation.RequestMapping *) * *(..))")
    public void methodsWithRequestMapping(){}

    @Around(value = "restControllers() && methodsWithRequestMapping()")
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
    public void logClients(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String requestUrl = request.getScheme() + "://" + request.getServerName()
                            + ":" + request.getServerPort() + request.getContextPath() + request.getRequestURI();
        if (!StringUtils.isEmpty(request.getQueryString())) {
            requestUrl += "?" + request.getQueryString();
        }

        ClientMeasurement clientMeasurement = new ClientMeasurement(request.getRemoteAddr(), requestUrl);
        publishEvent(MeasurementType.REQUEST, clientMeasurement);
    }

    @After("methodsWithRequestMapping()")
    public void logResponses(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
            String requestUrl = request.getScheme() + "://" + request.getServerName()
                                + ":" + request.getServerPort() + request.getContextPath() + request.getRequestURI()
                                + "?" + request.getQueryString();

            String clientIp = request.getRemoteAddr();
        String clientRequest;
        clientRequest = request.getRemoteHost() + request.getRemoteAddr();
        System.out.println("RESPONSE " + requestUrl + " - " + clientIp + " - " + clientRequest);

        int httpResponseStatus = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse().getStatus();
        System.out.println(httpResponseStatus);
        }

        // Not providing proper exception code for now
//    @AfterThrowing(pointcut = "restControllers() && methodsWithRequestMapping()", throwing = "exception")
//    public void logExceptionsMoreData(JoinPoint joinPoint, Exception exception) {
//        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
//    }
}
