package org.ametyst.metrics.storage;

import org.ametyst.metrics.util.DateUtil;
import org.ametyst.metrics.export.ToCsvExporter;
import org.ametyst.metrics.measurement.Measurement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class DiskPersistComponent {
    @Autowired
    private ToCsvExporter toCsvExporter;
    @Autowired
    private InMemoryStorage inMemoryStorage;

    @Scheduled(cron = "${scheduled.persist}")
    public void persist() {
        // TODO: Migrate it to not care about the current date, but only persist the remaining entries
        String now = DateUtil.getPreviousMinute();
        List<Measurement> detailedMeasurementsList = inMemoryStorage.getAllMeasurementsList(now);
        persist(now, detailedMeasurementsList);
    }

    private void persist(String dateTimeKey, List<Measurement> measurements) {
        Map<Class<? extends Measurement>, List<Measurement>> measurementsByClass = new HashMap<>();
        measurements.forEach(m -> measurementsByClass.computeIfAbsent(m.getClass(), me -> new ArrayList<>()).add(m));
        measurementsByClass.forEach((key, value) -> {
            try {
                toCsvExporter.exportSingleMeasurementType(dateTimeKey, key, value);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
}
