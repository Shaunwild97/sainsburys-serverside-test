package com.sainsburys.techtest.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.joda.money.Money;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class Total {
    private Money gross;

    public Money getVAT() {
        return gross.multipliedBy(1/6D, RoundingMode.HALF_UP);
    }
}
