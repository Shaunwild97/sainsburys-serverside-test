package com.sainsburys.techtest.exceptions;

import java.security.InvalidParameterException;

public class ProductParseException extends InvalidParameterException {

    public ProductParseException(String message) {
        super(message);
    }

}
