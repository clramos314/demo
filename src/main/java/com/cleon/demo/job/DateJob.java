package com.cleon.demo.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class DateJob {

    @Scheduled(initialDelayString = "${demo.sleep.initial}", fixedRateString = "${demo.sleep.repetition}")
    @Async
    public void refreshDate() {
        log.info("Test job executed at " + new Date());
    }

}
