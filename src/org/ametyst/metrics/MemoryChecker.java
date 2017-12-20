package org.ametyst.metrics;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableLogging
public class MemoryChecker {
    @Scheduled(cron = "*/5 * * * * *")
    public void checkMemoryState() {
        String value = "Total - " + Runtime.getRuntime().totalMemory() + "" +
                       "MAX - " + Runtime.getRuntime().maxMemory() + "" +
                       "FREE - " + Runtime.getRuntime().freeMemory();
        System.out.println(value);
    }
}
