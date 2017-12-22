package org.ametyst.metrics.measurement;

import java.io.Serializable;

public abstract class Measurement implements Serializable {
    private Long time;

    protected Measurement(Long time) {
        this.time = time;
    }

    public Long getTime() {
        return time;
    }
}
