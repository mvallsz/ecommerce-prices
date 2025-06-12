package com.bcnc.ecommerceprices.infrastructure.adapter.controller.web.dto;

import java.time.LocalDateTime;

/**
 * DTO for transferring error information from the web controllers.
 * Represents the structure of error responses for price-related queries.
 */
public class PriceErrorResponse {
    private final LocalDateTime timestamp;
    private final int status;
    private final String error;
    private final String message;
    private final String path;

    /**
     * Constructs a new PriceErrorResponse DTO.
     *
     * @param status   the HTTP status code
     * @param error    the error description
     * @param message  the error message
     * @param path     the request path that caused the error
     */
    public PriceErrorResponse(int status, String error, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public LocalDateTime getTimestamp() { return this.timestamp; }

    public int getStatus() { return this.status; }

    public String getError() { return this.error; }

    public String getMessage() { return this.message; }

    public String getPath() { return this.path; }
}