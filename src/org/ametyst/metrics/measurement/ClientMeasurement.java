package org.ametyst.metrics.measurement;

public class ClientMeasurement implements Measurement {
    private String ip;
    private String url;
    private String queryParam;
    private String system;
    private String device;
    private String browser;
    private String browserVersion;

    public ClientMeasurement(String ip, String url, String queryParam, String system, String device, String browser, String browserVersion) {
        this.ip = ip;
        this.url = url;
        this.queryParam = queryParam;
        this.system = system;
        this.device = device;
        this.browser = browser;
        this.browserVersion = browserVersion;
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

    public String getSystem() {
        return system;
    }

    public String getDevice() {
        return device;
    }

    public String getBrowser() {
        return browser;
    }

    public String getBrowserVersion() {
        return browserVersion;
    }
}
