package com.homework.payment.schedulingtasks;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.homework.payment.service.PaymentApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private PaymentApplicationService paymentApplicationService;

    public ScheduledTasks(final PaymentApplicationService paymentApplicationService) {
        this.paymentApplicationService = paymentApplicationService;
    }

    @Scheduled(fixedDelay = 60000)
    public void reportCurrentTime() {
        System.out.print(paymentApplicationService.printPaymentsRecords());
        LOGGER.info("The time is now {}", dateFormat.format(new Date()));
    }

}