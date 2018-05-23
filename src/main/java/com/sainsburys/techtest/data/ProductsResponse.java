package com.sainsburys.techtest.data;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@EqualsAndHashCode
public class ProductsResponse {
    private List<Product> results;

    public Total getTotal() {
        Money gross = results
                .stream()
                .map(r -> r.getUnitPrice())
                .reduce(Money.zero(CurrencyUnit.GBP), (a,b)->a.plus(b));

        return new Total(gross);
    }
}
