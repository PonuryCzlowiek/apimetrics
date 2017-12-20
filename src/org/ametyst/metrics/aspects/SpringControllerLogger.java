package org.ametyst.metrics.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Aspect
@Component
@RequiredTypes(value = {"org.springframework.stereotype.Controller",
        "org.springframework.web.bind.annotation.RequestMapping"})
public class SpringControllerLogger {

    // any method within controller or its specialization
    @Pointcut("within(@(@org.springframework.stereotype.Controller *) *)")
    public void restControllers(){}

    // all methods with @RequestMapping or its specialization
    @Pointcut("execution(@(@org.springframework.web.bind.annotation.RequestMapping *) * *(..))")
    public void methodsWithRequestMapping(){}

    @Around(value = "restControllers() && methodsWithRequestMapping()")
    public Object measureControllerTime(ProceedingJoinPoint joinPoint) throws Throwable{
        long start = System.currentTimeMillis();

        Object proceed = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - start;

        System.out.println(joinPoint.getSignature() + " executed in " + executionTime + "ms");
        return proceed;
    }

    @Before(value = "methodsWithRequestMapping()")
    public void logClients(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String requestUrl = request.getScheme() + "://" + request.getServerName()
                            + ":" + request.getServerPort() + request.getContextPath() + request.getRequestURI()
                            + "?" + request.getQueryString();

        String clientIp = request.getRemoteAddr();
        String clientRequest;
        clientRequest = request.getRemoteHost() + request.getRemoteAddr();
        System.out.println("REQUEST " + requestUrl + " - " + clientIp + " - " + clientRequest);
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

//    @AfterThrowing(pointcut = "restControllers() && methodsWithRequestMapping() && args(responseObj,..)", throwing = "exception")
    @AfterThrowing(pointcut = "restControllers() && methodsWithRequestMapping()", throwing = "exception")
    public void logExceptionsMoreData(JoinPoint joinPoint, Exception exception) {
        System.out.println("Się rypło w " + joinPoint.getSignature() + " - " + exception.getMessage());
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
        System.out.println(response.getStatus());
    }

//    @After(value = "execution(* org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver+.*(..))")
//@After(value = "within(org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver+)")
//    @After(value = "within(org.springframework..*ExceptionResolver) && args(Exception,..)")
//    public void a() {
//        System.out.println("WO");
//    }
}
