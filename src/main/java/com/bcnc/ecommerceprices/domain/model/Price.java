package com.bcnc.ecommerceprices.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

// Entidad de Dominio Pura para Price
public class Price {

    private final Brand brand;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final Integer priceList;
    private final Long productId;
    private final Integer priority;
    private final Double price;
    private final String curr;

    public Price(Brand brand, LocalDateTime startDate, LocalDateTime endDate, Integer priceList, Long productId, Integer priority, Double price, String curr) {
        this.brand = brand;
        this.startDate = startDate;
        this.endDate = endDate;
        this.priceList = priceList;
        this.productId = productId;
        this.priority = priority;
        this.price = price;
        this.curr = curr;
    }

    public Brand getBrand() {
        return this.brand;
    }

    public LocalDateTime getStartDate() {
        return this.startDate;
    }

    public LocalDateTime getEndDate() {
        return this.endDate;
    }

    public Integer getPriceList() {
        return this.priceList;
    }

    public Long getProductId() {
        return this.productId;
    }

    public Integer getPriority() {
        return this.priority;
    }

    public Double getPrice() {
        return this.price;
    }

    public String getCurr() {
        return this.curr;
    }

    public boolean appliesTo(LocalDateTime applicationDate) {
        return !applicationDate.isBefore(this.startDate) && !applicationDate.isAfter(this.endDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Price price1 = (Price) o;
        return Objects.equals(this.brand, price1.brand) &&
                Objects.equals(this.startDate, price1.startDate) &&
                Objects.equals(this.endDate, price1.endDate) &&
                Objects.equals(this.priceList, price1.priceList) &&
                Objects.equals(this.productId, price1.productId) &&
                Objects.equals(this.priority, price1.priority) &&
                Objects.equals(this.price, price1.price) &&
                Objects.equals(this.curr, price1.curr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.brand, this.startDate, this.endDate, this.priceList, this.productId, this.priority, this.price, this.curr);
    }

    @Override
    public String toString() {
        return "Price{" +
                "brand=" + this.brand +
                ", startDate=" + this.startDate +
                ", endDate=" + this.endDate +
                ", priceList=" + this.priceList +
                ", productId=" + this.productId +
                ", priority=" + this.priority +
                ", price=" + this.price +
                ", curr='" + this.curr + '\'' +
                '}';
    }
}