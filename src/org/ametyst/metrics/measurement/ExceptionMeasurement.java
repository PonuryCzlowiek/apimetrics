package org.ametyst.metrics.measurement;

public class ExceptionMeasurement implements Measurement {
    private String className;
    private String methodName;
    private String responseType;

    public ExceptionMeasurement(String className, String methodName, String responseType) {
        this.className = className;
        this.methodName = methodName;
        this.responseType = responseType;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getResponseType() {
        return responseType;
    }
}
