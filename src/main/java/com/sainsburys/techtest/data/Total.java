package com.sainsburys.techtest.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class Total {
    private BigDecimal gross;

    public BigDecimal getVAT() {
        return gross
                .divide(new BigDecimal("120"), 3, RoundingMode.HALF_UP)
                .multiply(new BigDecimal("20"))
                .stripTrailingZeros();
    }
}
