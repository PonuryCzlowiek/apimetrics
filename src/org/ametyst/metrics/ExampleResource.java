package org.ametyst.metrics;

import org.ametyst.metrics.measurement.Measurement;
import org.ametyst.metrics.measurement.MeasurementType;
import org.ametyst.metrics.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class ExampleResource {
    @Autowired
    private Storage storage;

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

    @GetMapping("throwExceptionWithResponse")
    public String throwExceptionWithMoreData(HttpServletResponse response) throws Exception {
        throw new Exception("");
    }

    @GetMapping("measurements")
    public List<Measurement> getMeasurements(@RequestParam MeasurementType measurementType) {
        return storage.getMeasurements(measurementType);
    }
}
