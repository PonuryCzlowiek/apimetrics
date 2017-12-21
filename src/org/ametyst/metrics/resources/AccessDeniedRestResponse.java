package org.ametyst.metrics.resources;

import java.io.Serializable;

public class AccessDeniedRestResponse implements Serializable {
    private String errorMessage;

    public AccessDeniedRestResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
