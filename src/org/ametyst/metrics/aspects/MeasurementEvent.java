package org.ametyst.metrics.aspects;

import org.ametyst.metrics.measurement.Measurement;
import org.ametyst.metrics.measurement.MeasurementType;
import org.springframework.context.ApplicationEvent;

public class MeasurementEvent extends ApplicationEvent {
    private final MeasurementType measurementType;
    private final Measurement measurement;

    public MeasurementEvent(Object source, MeasurementType measurementType, Measurement measurement) {
        super(source);
        this.measurementType = measurementType;
        this.measurement = measurement;
    }

    public Measurement getMeasurement() {
        return measurement;
    }

    public MeasurementType getMeasurementType() {
        return measurementType;
    }
}
