package org.ametyst.metrics.storage;

import org.ametyst.metrics.measurement.Measurement;
import org.ametyst.metrics.measurement.MeasurementType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

@Component
public class InMemoryStorage implements Storage {
    private static MultiValueMap<MeasurementType, Measurement> memory = new LinkedMultiValueMap<>();

    @Override
    public synchronized void store(MeasurementType measurementType, Measurement o) {
        memory.add(measurementType, o);
    }

    @Override
    public List<Measurement> getMeasurements(MeasurementType measurementType) {
        return memory.getOrDefault(measurementType, new ArrayList<>());
    }
}
