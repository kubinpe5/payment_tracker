package com.homework.payment.repository;

import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class PaymentRecord {

    final String currency;
    final BigDecimal amount;

    public PaymentRecord(@NotNull @Size(min = 3, max = 3) final String currency, @NotNull final BigDecimal amount) {
        this.currency = currency;
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
