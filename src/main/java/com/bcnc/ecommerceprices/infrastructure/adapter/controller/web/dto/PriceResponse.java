package com.bcnc.ecommerceprices.infrastructure.adapter.controller.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

/**
 * DTO for transferring price data from the domain layer to the web controllers.
 * Represents the response structure for price-related queries.
 */
public class PriceResponse {

    private Long productId;
    private Long brandId;
    private Integer priceList;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;
    private Double price;
    private String currency;

    /**
     * Default constructor for deserialization.
     */
    public PriceResponse() {
    }

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

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getBrandId() {
        return this.brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Integer getPriceList() {
        return this.priceList;
    }

    public void setPriceList(Integer priceList) {
        this.priceList = priceList;
    }

    public LocalDateTime getStartDate() {
        return this.startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return this.endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}