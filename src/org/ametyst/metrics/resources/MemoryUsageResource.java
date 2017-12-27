package org.ametyst.metrics.resources;

import org.ametyst.metrics.measurement.Measurement;
import org.ametyst.metrics.measurement.MeasurementType;
import org.ametyst.metrics.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MemoryUsageResource {
    @Autowired
    @Qualifier("InMemoryStorage")
    private Storage storage;

    @GetMapping("memoryUsage")
    public List<Measurement> getMeasurements() {
        return storage.getDetailedMeasurementsList(MeasurementType.MEMORY);
    }
}
