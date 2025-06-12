package com.bcnc.ecommerceprices.infrastructure.adapter.controller.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

/**
 * DTO for transferring price data from the domain layer to the web controllers.
 * Represents the response structure for price-related queries.
 */
public class PriceResponse {

    private final Long productId;
    private final Long brandId;
    private final Integer priceList;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime startDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime endDate;
    private final Double price;
    private final String currency;

    /**
     * Constructs a new PriceResponse DTO.
     *
     * @param productId  the ID of the product
     * @param brandId    the ID of the brand
     * @param priceList  the price list identifier
     * @param startDate  the start date of the price validity
     * @param endDate the end date of the price validity
     * @param price the price value
     * @param currency the currency of the price */
    public PriceResponse(Long productId, Long brandId, Integer priceList, LocalDateTime startDate, LocalDateTime endDate, Double price, String currency) {
        this.productId = productId;
        this.brandId = brandId;
        this.priceList = priceList;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.currency = currency;
    }

    public Long getProductId() {
        return this.productId;
    }

    public Long getBrandId() {
        return this.brandId;
    }

    public Integer getPriceList() {
        return this.priceList;
    }

    public LocalDateTime getStartDate() {
        return this.startDate;
    }

    public LocalDateTime getEndDate() {
        return this.endDate;
    }

    public Double getPrice() {
        return this.price;
    }

    public String getCurrency() {
        return this.currency;
    }

}