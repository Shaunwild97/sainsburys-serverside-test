package com.sainsburys.techtest.util;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.bind.config.PropertyNamingStrategy;
import javax.json.bind.config.PropertyOrderStrategy;

public class JsonbUtils {

    private static final Jsonb JSON_B = JsonbBuilder.
            newBuilder()
            .withConfig(new JsonbConfig()
                    .withFormatting(true)
                    .withPropertyOrderStrategy(PropertyOrderStrategy.LEXICOGRAPHICAL)
                    .withNullValues(true)
                    .withPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CASE_WITH_UNDERSCORES))
            .build();

    public static String toJson(Object object) {
        return JSON_B.toJson(object);
    }

    public static <T> T fromJson(String json, Class<T> returnType) {
        return JSON_B.fromJson(json, returnType);
    }
}
