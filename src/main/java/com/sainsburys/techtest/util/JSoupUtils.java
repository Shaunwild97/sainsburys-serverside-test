package com.sainsburys.techtest.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class JSoupUtils {

    private static final String HTTP = "http";
    private static final String UTF8 = "UTF-8";

    public static Document getDocument(String target) {
        try {
            if (target.startsWith(HTTP)) {
                return Jsoup.connect(target).get();
            } else {
                return Jsoup.parse(new File(JSoupUtils.class.getResource(target).toURI()), UTF8);
            }
        } catch (IOException | URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
