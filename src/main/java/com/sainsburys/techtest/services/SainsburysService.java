package com.sainsburys.techtest.services;

import com.sainsburys.techtest.data.Product;
import com.sainsburys.techtest.data.ProductsResponse;

public interface SainsburysService {
    ProductsResponse getProducts(String url);

    Product getProduct(String url);
}
