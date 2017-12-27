package org.ametyst.metrics.resources;

import org.ametyst.metrics.measurement.ClientMeasurement;
import org.ametyst.metrics.measurement.Measurement;
import org.ametyst.metrics.measurement.MeasurementType;
import org.ametyst.metrics.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ClientResource {
    @Autowired
    @Qualifier("InMemoryStorage")
    private Storage storage;

    @GetMapping("clients")
    public Map<String, Map<String, Integer>> getClients() {
        List<Measurement> measurements = storage.getDetailedMeasurementsList(MeasurementType.CLIENT);
        Map<String, Map<String, Integer>> fieldValueCounterMap = new HashMap<>();
        fieldValueCounterMap.put("ip", new HashMap<>());
        fieldValueCounterMap.put("url", new HashMap<>());
        fieldValueCounterMap.put("queryParam", new HashMap<>());
        fieldValueCounterMap.put("system", new HashMap<>());
        fieldValueCounterMap.put("device", new HashMap<>());
        fieldValueCounterMap.put("browser", new HashMap<>());

        measurements.forEach(m -> {
            ClientMeasurement e = (ClientMeasurement) m;
            fieldValueCounterMap.get("ip").compute(e.getIp(), (a, b) -> b == null ? 1 : b + 1);
            fieldValueCounterMap.get("url").compute(e.getUrl(), (a, b) -> b == null ? 1 : b + 1);
            fieldValueCounterMap.get("queryParam").compute(e.getQueryParam(), (a, b) -> b == null ? 1 : b + 1);
            fieldValueCounterMap.get("system").compute(e.getSystem(), (a, b) -> b == null ? 1 : b + 1);
            fieldValueCounterMap.get("device").compute(e.getDevice(), (a, b) -> b == null ? 1 : b + 1);
            fieldValueCounterMap.get("browser").compute(e.getBrowser() + " " + e.getBrowserVersion(), (a, b) -> b == null ? 1 : b + 1);
        });
        return fieldValueCounterMap;
    }
}
