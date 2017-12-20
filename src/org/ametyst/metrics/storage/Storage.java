package org.ametyst.metrics.storage;

import org.ametyst.metrics.measurement.Measurement;
import org.ametyst.metrics.measurement.MeasurementType;

import java.util.List;

public interface Storage {
    void store(MeasurementType measurementType, Measurement o);
    List<Measurement> getMeasurements(MeasurementType measurementType);
}
