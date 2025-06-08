package com.bcnc.ecommerceprices.infrastructure.adapter.controller;

import java.time.LocalDateTime;

public class PriceErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

    public PriceErrorResponse(int status, String error, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }


    public LocalDateTime getTimestamp() { return this.timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public int getStatus() { return this.status; }
    public void setStatus(int status) { this.status = status; }
    public String getError() { return this.error; }
    public void setError(String error) { this.error = error; }
    public String getMessage() { return this.message; }
    public void setMessage(String message) { this.message = message; }
    public String getPath() { return this.path; }
    public void setPath(String path) { this.path = path; }
}