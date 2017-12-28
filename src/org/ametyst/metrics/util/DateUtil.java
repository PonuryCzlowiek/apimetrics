package org.ametyst.metrics.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateUtil {
    private DateUtil() {}

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

    public static String getPreviousMinute() {
        return LocalDateTime.now().minus(1, ChronoUnit.MINUTES).format(DATE_TIME_FORMATTER);
    }

    public static String getNow() {
        return LocalDateTime.now().format(DATE_TIME_FORMATTER);
    }

    public static String format(LocalDateTime localDateTime) {
        return localDateTime.format(DATE_TIME_FORMATTER);
    }

    public static String format(Long milliseconds) {
        return Instant.ofEpochMilli(milliseconds).atZone(ZoneId.systemDefault()).format(DATE_TIME_FORMATTER);
    }
}
