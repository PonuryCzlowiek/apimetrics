package org.ametyst.metrics.measurement;

import org.ametyst.metrics.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MemoryChecker {
    @Autowired
    @Qualifier("InMemoryStorage")
    private Storage storage;

    @Scheduled(cron = "${scheduled.check.memory}")
    public void checkMemoryState() {
        MemoryMeasurement memoryMeasurement = new MemoryMeasurement(System.currentTimeMillis(),
                                                                    Runtime.getRuntime().totalMemory(),
                                                                    Runtime.getRuntime().maxMemory(),
                                                                    Runtime.getRuntime().freeMemory());
        storage.store(MeasurementType.MEMORY, memoryMeasurement);
    }
}
