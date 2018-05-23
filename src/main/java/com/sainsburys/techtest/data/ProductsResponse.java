package com.sainsburys.techtest.data;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@EqualsAndHashCode
public class ProductsResponse {
    private List<Product> results;

    public Total getTotal() {
        BigDecimal gross = results.stream()
                .map(p -> p.getUnitPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .stripTrailingZeros();

        return new Total(gross);
    }
}
