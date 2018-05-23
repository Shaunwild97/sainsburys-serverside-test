package com.sainsburys.techtest.tests;

import com.sainsburys.techtest.data.Product;
import com.sainsburys.techtest.data.ProductsResponse;
import com.sainsburys.techtest.services.SainsburysService;
import com.sainsburys.techtest.services.impl.SainsburysServiceJsoupImpl;
import com.sainsburys.techtest.util.JsonbUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Shaun
 * <p>
 * All tests within this class assume that the given website will always have
 * the same contents and will simply make sure that the return list of products
 * has the correct values and excludes incorrect values.
 */
public class SainsburysTests {

    private final List<Product> expectedProducts = new ArrayList<>();
    private final SainsburysService testService = new SainsburysServiceJsoupImpl();

    private final String MOCK_URL = "/jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";

    private ProductsResponse actualResponse;

    @Before
    public void preInitialize() {
        expectedProducts.add(new Product("Sainsbury's Strawberries 400g", 33, new BigDecimal("1.75"), "by Sainsbury's strawberries"));
        expectedProducts.add(new Product("Sainsbury's Blueberries 200g", 45, new BigDecimal("1.75"), "by Sainsbury's blueberries"));
        expectedProducts.add(new Product("Sainsbury's Raspberries 225g", 32, new BigDecimal("1.75"), "by Sainsbury's raspberries"));
        expectedProducts.add(new Product("Sainsbury's Blackberries, Sweet 150g", 32, new BigDecimal("1.50"), "by Sainsbury's blackberries"));
        expectedProducts.add(new Product("Sainsbury's Blueberries 400g", 45, new BigDecimal("3.25"), "by Sainsbury's blueberries"));
        expectedProducts.add(new Product("Sainsbury's Blueberries, SO Organic 150g", 45, new BigDecimal("2.00"), "So Organic blueberries"));
        expectedProducts.add(new Product("Sainsbury's Raspberries, Taste the Difference 150g", 32, new BigDecimal("2.50"), "Ttd raspberries"));
        expectedProducts.add(new Product("Sainsbury's Cherries 400g", 52, new BigDecimal("2.50"), "by Sainsbury's Family Cherry Punnet"));
        expectedProducts.add(new Product("Sainsbury's Blackberries, Tangy 150g", 32, new BigDecimal("1.50"), "by Sainsbury's blackberries"));
        expectedProducts.add(new Product("Sainsbury's Strawberries, Taste the Difference 300g", 33, new BigDecimal("2.50"), "Ttd strawberries"));
        expectedProducts.add(new Product("Sainsbury's Cherry Punnet 200g", null, new BigDecimal("1.50"), "Cherries"));
        expectedProducts.add(new Product("Sainsbury's Mixed Berries 300g", null, new BigDecimal("3.50"), "by Sainsbury's mixed berries"));
        expectedProducts.add(new Product("Sainsbury's Mixed Berry Twin Pack 200g", null, new BigDecimal("2.75"), "Mixed Berries"));
        expectedProducts.add(new Product("Sainsbury's Redcurrants 150g", 71, new BigDecimal("2.50"), "by Sainsbury's redcurrants"));
        expectedProducts.add(new Product("Sainsbury's Cherry Punnet, Taste the Difference 200g", 48, new BigDecimal("2.50"), "Cherry Punnet"));
        expectedProducts.add(new Product("Sainsbury's Blackcurrants 150g", null, new BigDecimal("1.75"), null));
        expectedProducts.add(new Product("Sainsbury's British Cherry & Strawberry Pack 600g", null, new BigDecimal("4.00"), "British Cherry & Strawberry Mixed Pack"));

        //Uses the MOCK URL which points to a wget of the server, this way I know it will always be the same
        actualResponse = testService.getProducts(MOCK_URL);
    }

    @Test
    public void resultsContainAllTest() {
        assertEquals(expectedProducts, actualResponse.getResults());
    }

    @Test
    public void resultsExcludeCrossSellTest() {
        Product crossSell = new Product("Sainsbury's Klip Lock Storage Set 140ml x3", null, new BigDecimal(2.50), "100% airtight: Yes");

        Assert.assertFalse(actualResponse.getResults().contains(crossSell));
    }

    @Test
    public void resultsTotalCorrectTest() {
        BigDecimal expectedGross = new BigDecimal("39.5");
        BigDecimal expectedVAT = new BigDecimal("6.58");

        assertEquals(expectedGross, actualResponse.getTotal().getGross());
        assertEquals(expectedVAT, actualResponse.getTotal().getVAT());
    }

    @Test
    public void allValuesListed() {
        Product testProduct = new Product("Test Product", null, BigDecimal.ZERO, null);
        String output = JsonbUtils.toJson(testProduct);

        assertTrue(output.contains("unit_price"));
        assertTrue(output.contains("description"));
        assertTrue(output.contains("title"));

        assertFalse(output.contains("kcal_per_100g"));
    }
}
