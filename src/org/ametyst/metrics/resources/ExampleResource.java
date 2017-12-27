package org.ametyst.metrics.resources;

import org.ametyst.metrics.export.ToCsvExporter;
import org.ametyst.metrics.measurement.Measurement;
import org.ametyst.metrics.measurement.MeasurementType;
import org.ametyst.metrics.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.expression.AccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Random;

@RestController
public class ExampleResource {
    @Autowired
    @Qualifier("InMemoryStorage")
    private Storage storage;
    @Autowired
    private ToCsvExporter toCsvExporter;

    @GetMapping("resourceWithRequest")
    public String getResource(HttpServletRequest httpServletRequest) {
        return "ok";
    }

    @GetMapping("resourceWithResponse")
    public String getResourceWithResponseDeclared(HttpServletResponse response) {
        return "ok";
    }

    @GetMapping("throwException")
    public String throwException() throws Exception {
        throw new Exception("");
    }

    @GetMapping("throwSpecificException")
    public String throwExceptionWithMoreData(HttpServletResponse response) throws Exception {
        if (new Random().nextBoolean()) {
            throw new AccessException("");
        } else {
            throw new FileNotFoundException();
        }
    }

    @GetMapping("measurements")
    public List<Measurement> getMeasurements(@RequestParam MeasurementType measurementType) {
        return storage.getDetailedMeasurementsList(measurementType);
    }

    @GetMapping("exportToCsv")
    public String exportMeasurements(@RequestParam MeasurementType measurementType) {
        List<Measurement> measurements = storage.getDetailedMeasurementsList(measurementType);
        toCsvExporter.export(measurements);
        return "ok";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public AccessDeniedRestResponse handleException(Exception e) {
        return new AccessDeniedRestResponse("Access denied");
    }
}
