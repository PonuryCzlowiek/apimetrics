package org.ametyst.metrics.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateUtil {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

    public static String getPreviousMinute() {
        return LocalDateTime.now().minus(1, ChronoUnit.MINUTES).format(DATE_TIME_FORMATTER);
    }

    public static String getNow() {
       return LocalDateTime.now().format(DATE_TIME_FORMATTER);
    }

    public static String parse(Long milliseconds) {
        return Instant.ofEpochMilli(milliseconds).atZone(ZoneId.systemDefault()).format(DATE_TIME_FORMATTER);
    }
}
