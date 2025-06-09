package com.bcnc.ecommerceprices.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Represents the Brand entity in the persistence layer.
 * This class is mapped to the "BRANDS" table in the database.
 * It is used to store and retrieve brand information.
 */
@Entity
@Table(name = "BRANDS")
public class BrandEntity {

    /**
     * The unique identifier of the brand.
     * This field is mapped to the primary key of the "BRANDS" table.
     */
    @Id
    private Long id;

    /**
     * The name of the brand.
     * This field is mapped to the "NAME" column in the "BRANDS" table.
     */
    @Column(name = "NAME")
    private String name;

    /**
     * Default constructor for JPA.
     * Required for entity instantiation during database operations.
     */
    public BrandEntity() {
    }

    /**
     * Constructs a new BrandEntity with the specified id and name.
     *
     * @param id   the unique identifier of the brand
     * @param name the name of the brand
     */
    public BrandEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}