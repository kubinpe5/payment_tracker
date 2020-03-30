package com.homework.payment.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {

    private PaymentRepository paymentRepository;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();
        Map<String, BigDecimal> records = new HashMap<>();
        records.put("CZK", new BigDecimal("500"));
        records.put("USD", new BigDecimal("100"));
        records.put("EUR", new BigDecimal("200"));
        records.put("JPN", new BigDecimal("300"));
        records.put("AUS", new BigDecimal("400"));
        paymentRepository.setPaymentRecords(records);
    }

    @Test
    void addingRecordWithSameKeyWillAddToOriginalValue() {
        assertThat(paymentRepository.getPaymentRecords().size()).isEqualTo(5);
        paymentRepository.addRecord(new PaymentRecord("CZK", new BigDecimal("1")));
        assertThat(paymentRepository.getPaymentRecords().size()).isEqualTo(5);
        paymentRepository.addRecord(new PaymentRecord("HUN", new BigDecimal("1")));
        assertThat(paymentRepository.getPaymentRecords().size()).isEqualTo(6);
        assertThat(paymentRepository.getPaymentRecords().get("HUN")).isEqualTo(new BigDecimal("1"));
        assertThat(paymentRepository.getPaymentRecords().get("CZK")).isEqualTo(new BigDecimal("501"));
    }

    @Test
    void recordsArePrintedWithUSDConversion() {
        final String recordsToString = paymentRepository.printRecords();
        assertThat(recordsToString.contains("USD 100\n"));
        assertThat(recordsToString.contains("EUR 200.00 (USD 181.35400)\n"));
    }

    @Test
    void recordsWithZeroAmountAreNotPrinted() {
        paymentRepository.addRecord(new PaymentRecord("HUN", new BigDecimal("0")));
        final String recordsToString = paymentRepository.printRecords();
        assertThat(paymentRepository.getPaymentRecords().get("HUN")).isEqualTo("0");
        assertThat(!recordsToString.contains("HUN"));
    }
}