package org.ametyst.metrics.measurement;

import org.ametyst.metrics.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MemoryChecker {
    @Autowired
    private Storage storage;

    @Scheduled(cron = "*/5 * * * * *")
    public void checkMemoryState() {
        MemoryMeasurement memoryMeasurement = new MemoryMeasurement(Runtime.getRuntime().totalMemory(),
                                                                    Runtime.getRuntime().maxMemory(),
                                                                    Runtime.getRuntime().freeMemory());
        storage.store(MeasurementType.MEMORY, memoryMeasurement);
    }
}
