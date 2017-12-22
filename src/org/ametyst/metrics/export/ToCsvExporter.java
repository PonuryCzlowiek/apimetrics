package org.ametyst.metrics.export;

import org.ametyst.metrics.measurement.Measurement;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ToCsvExporter {
    private static final Logger LOG = LoggerFactory.getLogger(ToCsvExporter.class);

    public void export(List<Measurement> measurements) {
        if (measurements.isEmpty()) {
            return;
        }
        try {
            Map<Class<? extends Measurement>, List<Measurement>> measurementsByClass = new HashMap<>();
            measurements.forEach(m -> measurementsByClass.computeIfAbsent(m.getClass(), me -> new ArrayList<>()).add(m));

            for (Map.Entry<Class<? extends Measurement>, List<Measurement>> measurementsListWithType : measurementsByClass.entrySet()) {
                exportSingleMeasurementType(measurementsListWithType);
            }

        } catch (IOException e) {
            LOG.error("Export to CSV failed", e);
        }
    }

    private void exportSingleMeasurementType(Map.Entry<Class<? extends Measurement>, List<Measurement>> me) throws IOException {
        try (FileWriter file = new FileWriter(me.getKey().getTypeName() + ".csv");
            CSVPrinter csvPrinter = new CSVPrinter(file, CSVFormat.DEFAULT)) {
            // Reflections are used here so we do not care about the class as long as it follows java bean convention of getters
            List<Method> declaredMethods = new ArrayList<>();
            Class c = me.getKey();
            do {
                declaredMethods.addAll(Arrays.asList(c.getDeclaredMethods()));
                c = c.getSuperclass();

            } while (c != Object.class && c != null);
            List<Method> methods = (declaredMethods).stream().filter(method ->
                                                                                 (method.getName().startsWith("get") || method.getName().startsWith("is"))
                                                                                 && method.getParameterCount() == 0
                                                                        ).collect(Collectors.toList());
            // Headers - field names are taken from getters
            csvPrinter.printRecord(methods.stream()
                                          .map(m -> m.getName().substring("get".length()))
                                          .collect(Collectors.toList()));

            for (Measurement measurement : me.getValue()) {
                csvPrinter.printRecord(methods.stream().map(m -> {
                    try {
                        return String.valueOf(m.invoke(measurement));
                    } catch (IllegalAccessException|InvocationTargetException e) {
                        // intentionally left blank
                    }
                    return "";
                }).collect(Collectors.toList()));
            }
            csvPrinter.flush();
        }
    }
}
