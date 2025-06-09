package com.bcnc.ecommerceprices.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Domain entity representing a Price.
 * This class is part of the domain layer in a hexagonal architecture.
 */
public class Price {

    private final Brand brand;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final Integer priceList;
    private final Long productId;
    private final Integer priority;
    private final Double price;
    private final String currency;

    /**
     * Constructs a new Price entity.
     *
     * @param brand      the brand associated with the price
     * @param startDate  the start date of the price validity
     * @param endDate    the end date of the price validity
     * @param priceList  the price list identifier
     * @param productId  the ID of the product
     * @param priority   the priority of the price
     * @param price      the price value
     * @param currency   the currency of the price
     * @throws IllegalArgumentException if any parameter is null or invalid
     */
    public Price(Brand brand, LocalDateTime startDate, LocalDateTime endDate, Integer priceList, Long productId,
                 Integer priority, Double price, String currency) {
        this.brand = Objects.requireNonNull(brand, "Brand cannot be null");
        this.startDate = validateDate(startDate, "Start date cannot be null");
        this.endDate = validateDate(endDate, "End date cannot be null");
        this.priceList = validatePositive(priceList, "Price list must be a positive number");
        this.productId = validatePositive(productId, "Product ID must be a positive number");
        this.priority = validatePositive(priority, "Priority must be a positive number");
        this.price = validatePositive(price, "Price must be a positive number");
        this.currency = validateCurrency(currency);
    }

    public Brand getBrand() {
        return brand;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public Integer getPriceList() {
        return priceList;
    }

    public Long getProductId() {
        return productId;
    }

    public Double getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }

    /**
     * Checks if the price applies to the given application date.
     *
     * @param applicationDate the date to check
     * @return true if the price is valid for the given date, false otherwise
     */
    public boolean appliesTo(LocalDateTime applicationDate) {
        return !applicationDate.isBefore(startDate) && !applicationDate.isAfter(endDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price1 = (Price) o;
        return Objects.equals(brand, price1.brand) &&
                Objects.equals(startDate, price1.startDate) &&
                Objects.equals(endDate, price1.endDate) &&
                Objects.equals(priceList, price1.priceList) &&
                Objects.equals(productId, price1.productId) &&
                Objects.equals(priority, price1.priority) &&
                Objects.equals(price, price1.price) &&
                Objects.equals(currency, price1.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brand, startDate, endDate, priceList, productId, priority, price, currency);
    }

    @Override
    public String toString() {
        return "Price{" +
                "brand=" + brand +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", priceList=" + priceList +
                ", productId=" + productId +
                ", priority=" + priority +
                ", price=" + price +
                ", currency='" + currency + '\'' +
                '}';
    }

    private static LocalDateTime validateDate(LocalDateTime date, String errorMessage) {
        if (date == null) {
            throw new IllegalArgumentException(errorMessage);
        }
        return date;
    }

    private static <T extends Number> T validatePositive(T value, String errorMessage) {
        if (value == null || value.doubleValue() < 0) {
            throw new IllegalArgumentException(errorMessage);
        }
        return value;
    }

    private static String validateCurrency(String currency) {
        if (currency == null || currency.trim().isEmpty()) {
            throw new IllegalArgumentException("Currency cannot be null or empty");
        }
        return currency;
    }
}