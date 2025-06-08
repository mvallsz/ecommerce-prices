package com.bcnc.ecommerceprices.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "PRICES")
public class PriceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "BRAND_ID", referencedColumnName = "ID")
    private BrandEntity brand;

    @Column(name = "START_DATE")
    private LocalDateTime startDate;
    @Column(name = "END_DATE")
    private LocalDateTime endDate;
    @Column(name = "PRICE_LIST")
    private Integer priceList;
    @Column(name = "PRODUCT_ID")
    private Long productId;
    @Column(name = "PRIORITY")
    private Integer priority;
    @Column(name = "PRICE")
    private Double price;
    @Column(name = "CURR")
    private String curr;

    public PriceEntity() {
    }

    public PriceEntity(BrandEntity brand, LocalDateTime startDate, LocalDateTime endDate, Integer priceList, Long productId, Integer priority, Double price, String curr) {
        this.brand = brand;
        this.startDate = startDate;
        this.endDate = endDate;
        this.priceList = priceList;
        this.productId = productId;
        this.priority = priority;
        this.price = price;
        this.curr = curr;
    }

    // Getters y Setters
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
    public String getCurr() { return this.curr; }
}