package org.ametyst.metrics.measurement;

public class ClientMeasurement implements Measurement {
    private String ip;
    private String url;

    public ClientMeasurement(String ip, String url) {
        this.ip = ip;
        this.url = url;
    }

    public String getIp() {
        return ip;
    }

    public String getUrl() {
        return url;
    }
}
