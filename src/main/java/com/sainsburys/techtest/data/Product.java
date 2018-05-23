package com.sainsburys.techtest.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.json.bind.annotation.JsonbProperty;
import java.math.BigDecimal;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class Product {

    private String title;

    @JsonbProperty(value = "kcal_per_100g", nillable = false)
    private Integer kcalPer100g;

    private BigDecimal unitPrice;

    private String description;
}
