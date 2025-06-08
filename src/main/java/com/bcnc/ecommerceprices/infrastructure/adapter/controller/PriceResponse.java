package com.bcnc.ecommerceprices.infrastructure.adapter.controller;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class PriceResponse {
    private Long productId;
    private Long brandId;
    private Integer priceList;
    @JsonFormat(pattern = "yyyy-MM-dd HH.mm.ss")
    private LocalDateTime startDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH.mm.ss")
    private LocalDateTime endDate;
    private Double finalPrice;
    private String currency;

    public PriceResponse(Long productId, Long brandId, Integer priceList, LocalDateTime startDate, LocalDateTime endDate, Double finalPrice, String currency) {
        this.productId = productId;
        this.brandId = brandId;
        this.priceList = priceList;
        this.startDate = startDate;
        this.endDate = endDate;
        this.finalPrice = finalPrice;
        this.currency = currency;
    }

    public Long getProductId() { return this.productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public Long getBrandId() { return this.brandId; }
    public void setBrandId(Long brandId) { this.brandId = brandId; }
    public Integer getPriceList() { return this.priceList; }
    public void setPriceList(Integer priceList) { this.priceList = priceList; }
    public LocalDateTime getStartDate() { return this.startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }
    public LocalDateTime getEndDate() { return this.endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }
    public Double getFinalPrice() { return this.finalPrice; }
    public void setFinalPrice(Double finalPrice) { this.finalPrice = finalPrice; }
    public String getCurrency() { return this.currency; }
    public void setCurrency(String currency) { this.currency = currency; }
}