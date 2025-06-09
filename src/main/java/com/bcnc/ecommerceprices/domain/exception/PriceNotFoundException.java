package com.bcnc.ecommerceprices.domain.exception;

/**
 * Exception thrown when no applicable price is found for a given product, brand, and application date.
 */
public class PriceNotFoundException extends RuntimeException {

    /**
     * Constructs a new PriceNotFoundException with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public PriceNotFoundException(String message) {
        super(message);
    }
}
