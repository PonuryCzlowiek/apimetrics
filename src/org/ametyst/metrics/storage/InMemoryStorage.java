package org.ametyst.metrics.storage;

import org.ametyst.metrics.util.DateUtil;
import org.ametyst.metrics.measurement.Measurement;
import org.ametyst.metrics.measurement.MeasurementType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This memory is short-term only
 */
@Component("InMemoryStorage")
public class InMemoryStorage implements Storage {
    private static Map<String, MultiValueMap<MeasurementType, Measurement>> memory = new HashMap<>();

    @Override
    public synchronized void store(MeasurementType measurementType, Measurement measurement) {
        String now = DateUtil.format(measurement.getTime());
        memory.compute(now, (key, multiValueMap) -> {
            MultiValueMap<MeasurementType, Measurement> tempMultiValueMap = multiValueMap;
            if (tempMultiValueMap == null) {
                tempMultiValueMap = new LinkedMultiValueMap();
            }
            tempMultiValueMap.compute(measurementType, (measurementTypeKey, measurementList) -> {
                List<Measurement> tempMeasurementList = measurementList;
                if (tempMeasurementList == null) {
                    tempMeasurementList = new ArrayList<>();
                }
                tempMeasurementList.add(measurement);
                return tempMeasurementList;
            });
            return tempMultiValueMap;
        });
    }

    @Override
    public List<Measurement> getDetailedMeasurementsList(MeasurementType measurementType) {
        return memory.entrySet()
                     .stream()
                     .flatMap(entry -> entry.getValue().computeIfAbsent(measurementType, type -> new ArrayList<>()).stream())
                     .collect(Collectors.toList());
    }

    @Override
    public List<Measurement> getAllMeasurementsList(String dateTimeKey) {
        return memory.getOrDefault(dateTimeKey, new LinkedMultiValueMap<>())
                     .entrySet()
                     .stream()
                     .flatMap(entry -> entry.getValue().stream())
                     .collect(Collectors.toList());
    }
}
