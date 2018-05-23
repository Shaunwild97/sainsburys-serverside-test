package com.sainsburys.techtest.util;

import org.joda.money.Money;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.bind.adapter.JsonbAdapter;
import javax.json.bind.config.PropertyNamingStrategy;
import javax.json.bind.config.PropertyOrderStrategy;

public class JsonbUtils {

    private static final Jsonb JSON_B = JsonbBuilder.
            newBuilder()
            .withConfig(new JsonbConfig()
                    .withFormatting(true)
                    .withPropertyOrderStrategy(PropertyOrderStrategy.LEXICOGRAPHICAL)
                    .withNullValues(true)
                    .withPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CASE_WITH_UNDERSCORES)
                    .withAdapters(new MoneyAdapter()))
            .build();

    public static String toJson(Object object) {
        return JSON_B.toJson(object);
    }

    public static <T> T fromJson(String json, Class<T> returnType) {
        return JSON_B.fromJson(json, returnType);
    }

    private static class MoneyAdapter implements JsonbAdapter<Money, String> {
        @Override
        public String adaptToJson(Money money) throws Exception {
            return money.getAmount().toPlainString();
        }

        @Override
        public Money adaptFromJson(String s) throws Exception {
            //Unnecessary deserializer
            return Money.parse("GBP " + s);
        }
    }
}
