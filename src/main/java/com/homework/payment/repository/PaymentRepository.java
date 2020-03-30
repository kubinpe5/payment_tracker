package com.homework.payment.repository;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

@Component
public class PaymentRepository {
    private static final char SPACE = ' ';
    private Map<String, BigDecimal> paymentRecords;

    public PaymentRepository() {
        paymentRecords = new HashMap<>();
    }

    public PaymentRepository(final Map<String, BigDecimal> paymentRecords) {
        this.paymentRecords = paymentRecords;
    }

    public Map<String, BigDecimal> getPaymentRecords() {
        return paymentRecords;
    }

    public void setPaymentRecords(final Map<String, BigDecimal> paymentRecords) {
        this.paymentRecords = paymentRecords;
    }

    public void addRecord(final PaymentRecord paymentRecord) {
        BigDecimal newAmount = paymentRecord.getAmount();
        if (paymentRecords.containsKey(paymentRecord.getCurrency())) {
            newAmount = paymentRecords.get(paymentRecord.getCurrency()).add(paymentRecord.getAmount());
        }
        paymentRecords.put(paymentRecord.getCurrency(), newAmount);
    }

    public String printRecords() {
        ExchangeRates exchangeRates = new ExchangeRates();
        Map<String, BigDecimal> exchangeRatesUsd = exchangeRates.getExchangeRateForUsd();
        StringBuilder sb = new StringBuilder();
        paymentRecords.forEach((key, value) -> {
            if (!value.equals(BigDecimal.ZERO) && exchangeRatesUsd.get(key) != null && !(new BigDecimal("1").equals(exchangeRatesUsd.get(key)))) {
                sb.append(key)
                        .append(SPACE)
                        .append(value.setScale(2, RoundingMode.HALF_UP))
                        .append(" (")
                        .append("USD ")
                        .append(exchangeRatesUsd.get(key).multiply(value))
                        .append(")\n");
            } else if (!value.equals(BigDecimal.ZERO)) {
                sb.append(key)
                        .append(SPACE)
                        .append(value)
                        .append("\n");
            }
        });
        return sb.toString();
    }
}
