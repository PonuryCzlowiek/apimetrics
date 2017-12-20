package org.ametyst.metrics.measurement;

public class MemoryMeasurement implements Measurement {
    private Long total;
    private Long max;
    private Long free;

    public MemoryMeasurement(Long total, Long max, Long free) {
        this.total = total;
        this.max = max;
        this.free = free;
    }

    public Long getTotal() {
        return total;
    }

    public Long getMax() {
        return max;
    }

    public Long getFree() {
        return free;
    }
}