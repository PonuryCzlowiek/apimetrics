package org.ametyst.metrics.aspects;

import org.ametyst.metrics.measurement.Measurement;
import org.ametyst.metrics.measurement.MeasurementType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

public abstract class AspectPublishingEvents {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    protected void publishEvent(final MeasurementType measurementType, final Measurement message) {
        MeasurementEvent measurementEvent = new MeasurementEvent(this, measurementType, message);
        applicationEventPublisher.publishEvent(measurementEvent);
    }
}
