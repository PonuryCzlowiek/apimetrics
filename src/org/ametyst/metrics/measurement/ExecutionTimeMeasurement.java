package org.ametyst.metrics.measurement;

public class ExecutionTimeMeasurement extends Measurement {
    private String className;
    private String methodName;
    private Long executionTime;

    public ExecutionTimeMeasurement(Long time, String className, String methodName, Long executionTime) {
        super(time);
        this.className = className;
        this.methodName = methodName;
        this.executionTime = executionTime;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public Long getExecutionTime() {
        return executionTime;
    }
}
