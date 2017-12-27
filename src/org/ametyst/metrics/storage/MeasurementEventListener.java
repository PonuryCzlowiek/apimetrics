package org.ametyst.metrics.storage;

import org.ametyst.metrics.aspect.MeasurementEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class MeasurementEventListener implements ApplicationListener<MeasurementEvent>{
    @Autowired
    private Storage storage;
    public void onApplicationEvent(MeasurementEvent event) {
        storage.store(event.getMeasurementType(), event.getMeasurement());
    }
}
