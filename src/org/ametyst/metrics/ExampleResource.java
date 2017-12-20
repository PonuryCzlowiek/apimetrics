package org.ametyst.metrics;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class ExampleResource {
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
}
