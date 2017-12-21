package org.ametyst.metrics.measurement;

public class ClientMeasurement implements Measurement {
    private String ip;
    private String url;
    private String queryParam;

    public ClientMeasurement(String ip, String url, String queryParam) {
        this.ip = ip;
        this.url = url;
        this.queryParam = queryParam;
    }

    public String getIp() {
        return ip;
    }

    public String getUrl() {
        return url;
    }

    public String getQueryParam() {
        return queryParam;
    }
}
