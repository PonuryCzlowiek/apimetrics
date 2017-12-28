package org.ametyst.metrics.resources;

import org.ametyst.metrics.measurement.ExceptionMeasurement;
import org.ametyst.metrics.measurement.Measurement;
import org.ametyst.metrics.measurement.MeasurementType;
import org.ametyst.metrics.storage.Storage;
import org.ametyst.metrics.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class ExceptionsOccurrencesResource {
    @Autowired
    @Qualifier("InMemoryStorage")
    private Storage storage;

    @GetMapping("exceptionsOccurrences")
    public Map<Long, Map<String, Integer>> getExceptionsOccurrences() {
        List<Measurement> detailedMeasurementsList = storage.getDetailedMeasurementsList(MeasurementType.EXCEPTION);
        LocalDateTime now = LocalDateTime.now();
        Map<String, Map<String, Integer>> exceptionsPerMinuteGrouped = new LinkedHashMap<>();
        List<String> exceptionTypes = detailedMeasurementsList.stream()
                                                              .map(m -> ((ExceptionMeasurement) m).getResponseType())
                                                              .distinct()
                                                              .collect(Collectors.toList());
        for (int i=0; i<60; i++) {
            LocalDateTime current = now.minus(i, ChronoUnit.MINUTES);
            // labels - all 60 dates
            // values - 60 entries per type
            Map<String, Integer> occurrencesPerException = new HashMap<>();
            exceptionTypes.forEach(exceptionType -> occurrencesPerException.put(exceptionType, 0));
            exceptionsPerMinuteGrouped.put(DateUtil.format(current), occurrencesPerException);
        }
        if (exceptionTypes.isEmpty()) {
            return new HashMap<>();
        }
        detailedMeasurementsList.forEach(measurement -> {
            String parse = DateUtil.format(measurement.getTime());
            exceptionsPerMinuteGrouped.get(parse)
                               .compute(((ExceptionMeasurement)measurement).getResponseType(), (a, b) -> b == null ? 1 : b+1);
        });
        Map<Long, Map<String, Integer>> exceptionsPerMinute = new LinkedHashMap<>();
        exceptionsPerMinuteGrouped.entrySet()
                                  .stream()
                                  .forEach(a -> {
                                      long l = DateUtil.DATE_TIME_FORMATTER.parse(a.getKey(), LocalDateTime::from).atZone(ZoneId.systemDefault()).toEpochSecond() * 1000;
                                      exceptionsPerMinute.put(l, a.getValue());
                                  });
        return exceptionsPerMinute;
    }
}
