package com.homework.payment.repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ExchangeRates {

    public Map<String, BigDecimal> getExchangeRateForUsd() {
        Map<String, BigDecimal> exchangeRatesUSD = new HashMap<>();

        exchangeRatesUSD.put("USD", new BigDecimal("1"));
        exchangeRatesUSD.put("EUR", new BigDecimal("0.90677"));
        exchangeRatesUSD.put("GBP", new BigDecimal("0.81892"));
        exchangeRatesUSD.put("AUD", new BigDecimal("1.6474"));
        exchangeRatesUSD.put("CAD", new BigDecimal("1.4053"));
        exchangeRatesUSD.put("CZK", new BigDecimal("24.7115"));
        exchangeRatesUSD.put("CHF", new BigDecimal("0.96235"));
        exchangeRatesUSD.put("JPY", new BigDecimal("108.8450"));
        exchangeRatesUSD.put("HKD", new BigDecimal("7.75"));
        exchangeRatesUSD.put("RMB", new BigDecimal("7.09"));

        return exchangeRatesUSD;
    }
}
