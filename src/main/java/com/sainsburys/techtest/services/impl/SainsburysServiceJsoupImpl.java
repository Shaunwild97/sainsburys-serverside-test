package com.sainsburys.techtest.services.impl;

import com.sainsburys.techtest.data.Product;
import com.sainsburys.techtest.data.ProductsResponse;
import com.sainsburys.techtest.exceptions.ProductParseException;
import com.sainsburys.techtest.services.SainsburysService;
import com.sainsburys.techtest.util.JSoupUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static com.sainsburys.techtest.app.SainsburysApplication.TARGET_URL;

public class SainsburysServiceJsoupImpl implements SainsburysService {

    private static final String INVALID_PRODUCTS_ERROR = "Invalid products document specified";
    private static final String INVALID_PRODUCT_ERROR = "Product had invalid URL";
    private static final String PRODUCT_CLASS = ".product";
    private static final String PRICE_PER_UNIT_CLASS = ".pricePerUnit";
    private static final String TABLE_ROW_CLASS = ".tableRow0";
    private static final String PRODUCT_TEXT_CLASS = ".productText";
    private static final String LINK_TAG = "a";
    private static final String PARAGRAPH_TAG = "p";
    private static final String TABLE_COLUMN_TAG = "td";
    private static final String HREF_ATTR = "href";

    private static final String POUND_STRING = "£";
    private static final String EMPTY_STRING = "";
    private static final String UNIT_STRING = "/unit";

    @Override
    public ProductsResponse getProducts(String url) {
        Document document = JSoupUtils.getDocument(url);
        Elements productElements = document.select(PRODUCT_CLASS);

        if (productElements.isEmpty()) {
            throw new ProductParseException(INVALID_PRODUCTS_ERROR);
        }

        List<Product> products = productElements.stream()
                .map(this::processProductDom)
                .collect(Collectors.toList());

        return ProductsResponse.builder()
                .results(products)
                .build();
    }

    @Override
    public Product getProduct(String url) {
        Product result = new Product();
        Document document = JSoupUtils.getDocument(url);

        result.setKcalPer100g(getProductKcal(document));
        result.setDescription(getProductDescription(document));

        return result;
    }

    private Integer getProductKcal(Document document) {
        Elements tableRowsElements = document.select(TABLE_ROW_CLASS);
        Elements productCalsElements = tableRowsElements.select(TABLE_COLUMN_TAG);

        if (!productCalsElements.isEmpty()) {
            String productCals = productCalsElements.get(0).text();
            return parseKcal(productCals);
        }

        return null;
    }

    private String getProductDescription(Document document) {
        Elements descriptionElements = document.select(PRODUCT_TEXT_CLASS);
        String productDescription = descriptionElements.select(PARAGRAPH_TAG).get(0).text();

        if (!productDescription.isEmpty()) {
            return productDescription;
        }

        return null;
    }

    private Product processProductDom(Element productElement) {
        Elements linkElements = productElement.select(LINK_TAG);

        String productLink = linkElements.attr(HREF_ATTR);

        URI path;

        try {
            path = new URI(TARGET_URL).resolve(productLink);
        } catch (URISyntaxException ex) {
            throw new ProductParseException(INVALID_PRODUCT_ERROR);
        }

        productLink = path.toString();

        Product product = getProduct(productLink);

        product.setTitle(getProductTitle(productElement));
        product.setUnitPrice(getProductUnitPrice(productElement));

        return product;
    }

    private BigDecimal getProductUnitPrice(Element element) {
        return parseUnitPrice(element.select(PRICE_PER_UNIT_CLASS).get(0).text());
    }

    private String getProductTitle(Element element) {
        return element.select(LINK_TAG).get(0).text();
    }

    private int parseKcal(String kcal) {
        String kcalString = kcal.substring(0, kcal.length() - 4);
        return Integer.parseInt(kcalString);
    }

    private BigDecimal parseUnitPrice(String unitPrice) {
        String result = unitPrice
                .replace(POUND_STRING, EMPTY_STRING)
                .replace(UNIT_STRING, EMPTY_STRING);

        return new BigDecimal(result);
    }
}
