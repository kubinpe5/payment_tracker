package com.homework.payment.service;

import com.homework.payment.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.ApplicationContext;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class PaymentApplicationServiceTest {

    private PaymentRepository paymentRepository;

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private ApplicationArguments applicationArguments;

    private PaymentApplicationService service;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();
        service = new PaymentApplicationService(applicationContext, applicationArguments, paymentRepository);
    }

    @Test
    void initPaymentRepository() {
        assertThat(paymentRepository.getPaymentRecords().size()).isEqualTo(3);
        final Map<String, BigDecimal> referenceRecords = new HashMap<>();
        referenceRecords.put("USD", new BigDecimal("900"));
        referenceRecords.put("RMB", new BigDecimal("2000"));
        referenceRecords.put("HKD", new BigDecimal("300"));
        assertThat(paymentRepository.getPaymentRecords()).isEqualTo(referenceRecords);
    }

    @Test
    void printingRecordsInServiceDoesNotUseAnyLogicBeyondRepository() {
        assertThat(service.printPaymentsRecords()).isEqualTo("HKD 300.00 (USD 2325.00)\n" +
                "RMB 2000.00 (USD 14180.00)\n" +
                "USD 900\n");
    }
}
