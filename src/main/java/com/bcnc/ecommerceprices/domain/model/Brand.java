package com.bcnc.ecommerceprices.domain.model;

import java.util.Objects;

/**
 * Domain entity representing a Brand.
 * This class is part of the domain layer in a hexagonal architecture.
 */
public class Brand {
    private final Long id;
    private final String name;

    /**
     * Constructs a new Brand entity.
     *
     * @param id   the unique identifier of the brand
     * @param name the name of the brand
     * @throws IllegalArgumentException if id is null or less than or equal to 0, or if name is null or empty
     */
    public Brand(Long id, String name) {
        this.id = validateId(id);
        this.name = validateName(name);
    }

    /**
     * Gets the unique identifier of the brand.
     *
     * @return the brand ID
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Gets the name of the brand.
     *
     * @return the brand name
     */
    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Brand brand = (Brand) o;
        return Objects.equals(this.id, brand.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return "Brand{" +
                "id=" + this.id +
                ", name='" + this.name + '\'' +
                '}';
    }

    /**
     * Validates the brand ID.
     *
     * @param id the brand ID
     * @return the validated ID
     * @throws IllegalArgumentException if id is null or less than or equal to 0
     */
    private static Long validateId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Brand ID must be a positive number");
        }
        return id;
    }

    /**
     * Validates the brand name.
     *
     * @param name the brand name
     * @return the validated name
     * @throws IllegalArgumentException if name is null or empty
     */
    private static String validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Brand name cannot be null or empty");
        }
        return name;
    }
}