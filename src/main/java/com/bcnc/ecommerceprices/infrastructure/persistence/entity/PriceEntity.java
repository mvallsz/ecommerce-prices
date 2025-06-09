package com.bcnc.ecommerceprices.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

/**
 * Represents the Price entity in the persistence layer.
 * This class is mapped to the "PRICES" table in the database.
 * It is used to store and retrieve price information.
 */
@Entity
@Table(name = "PRICES")
public class PriceEntity {

    /**
     * The unique identifier of the price record.
     * This field is mapped to the primary key of the "PRICES" table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The brand associated with the price.
     * This field is mapped to the "BRAND_ID" column and references the "ID" column in the "BRANDS" table.
     */
    @ManyToOne
    @JoinColumn(name = "BRAND_ID", referencedColumnName = "ID")
    private BrandEntity brand;

    /**
     * The start date of the price validity period.
     * This field is mapped to the "START_DATE" column in the "PRICES" table.
     */
    @Column(name = "START_DATE")
    private LocalDateTime startDate;

    /**
     * The end date of the price validity period.
     * This field is mapped to the "END_DATE" column in the "PRICES" table.
     */
    @Column(name = "END_DATE")
    private LocalDateTime endDate;

    /**
     * The identifier of the price list.
     * This field is mapped to the "PRICE_LIST" column in the "PRICES" table.
     */
    @Column(name = "PRICE_LIST")
    private Integer priceList;

    /**
     * The unique identifier of the product.
     * This field is mapped to the "PRODUCT_ID" column in the "PRICES" table.
     */
    @Column(name = "PRODUCT_ID")
    private Long productId;

    /**
     * The priority of the price record.
     * This field is mapped to the "PRIORITY" column in the "PRICES" table.
     */
    @Column(name = "PRIORITY")
    private Integer priority;

    /**
     * The price value.
     * This field is mapped to the "PRICE" column in the "PRICES" table.
     */
    @Column(name = "PRICE")
    private Double price;

    /**
     * The currency of the price.
     * This field is mapped to the "CURR" column in the "PRICES" table.
     */
    @Column(name = "CURR")
    private String currency;

    /**
     * Default constructor for JPA.
     * Required for entity instantiation during database operations.
     */
    public PriceEntity() {
    }

    /**
     * Constructs a new PriceEntity with the specified attributes.
     *
     * @param brand      the brand associated with the price
     * @param startDate  the start date of the price validity period
     * @param endDate    the end date of the price validity period
     * @param priceList  the identifier of the price list
     * @param productId  the unique identifier of the product
     * @param priority   the priority of the price record
     * @param price      the price value
     * @param currency       the currency of the price
     */
    public PriceEntity(BrandEntity brand, LocalDateTime startDate, LocalDateTime endDate, Integer priceList, Long productId, Integer priority, Double price, String currency) {
        this.brand = brand;
        this.startDate = startDate;
        this.endDate = endDate;
        this.priceList = priceList;
        this.productId = productId;
        this.priority = priority;
        this.price = price;
        this.currency = currency;
    }

    public Long getId() { return this.id; }

    public void setId(Long id) { this.id = id; }

    public BrandEntity getBrand() { return this.brand; }

    public void setBrand(BrandEntity brand) { this.brand = brand; }

    public LocalDateTime getStartDate() { return this.startDate; }

    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }

    public LocalDateTime getEndDate() { return this.endDate; }

    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }

    public Integer getPriceList() { return this.priceList; }

    public void setPriceList(Integer priceList) { this.priceList = priceList; }

    public Long getProductId() { return this.productId; }

    public void setProductId(Long productId) { this.productId = productId; }

    public Integer getPriority() { return this.priority; }

    public void setPriority(Integer priority) { this.priority = priority; }

    public Double getPrice() { return this.price; }

    public void setPrice(Double price) { this.price = price; }

    public String getCurrency() { return this.currency; }

    public void setCurrency(String currency) { this.currency = currency; }
}