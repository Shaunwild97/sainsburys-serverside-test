package com.sainsburys.techtest.app;

import com.sainsburys.techtest.data.ProductsResponse;
import com.sainsburys.techtest.services.SainsburysService;
import com.sainsburys.techtest.services.impl.SainsburysServiceJsoupImpl;
import com.sainsburys.techtest.util.JsonbUtils;

public class SainsburysApplication {
    public static final String TARGET_URL = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";

    public static void main(String[] args) {
        SainsburysService sainsburysService = new SainsburysServiceJsoupImpl();
        ProductsResponse response = sainsburysService.getProducts(TARGET_URL);

        String jsonOutput = JsonbUtils.toJson(response);
        System.out.println(jsonOutput);
    }
}
