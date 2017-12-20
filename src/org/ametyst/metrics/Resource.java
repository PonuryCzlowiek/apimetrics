package org.ametyst.metrics;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class Resource {
    @GetMapping("resource")
    public String getResource(HttpServletRequest httpServletRequest) {
        return "ok";
    }

    @GetMapping("resource2")
    public String getResourceWithResponseDeclared(HttpServletResponse response) {
        return "ok";
    }

    @GetMapping("throw")
    public String throwException() throws Exception {
        throw new Exception("No chyba nie");
    }

    @GetMapping("throw2")
    public String throwExceptionWithMoreData(HttpServletResponse response) throws Exception {
        throw new Exception("No chyba nie2");
    }
}
