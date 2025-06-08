package com.bcnc.ecommerceprices.domain.model;
import java.util.Objects;

// Entidad de Dominio Pura para Brand
public class Brand {
    private final Long id;
    private final String name;

    public Brand(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }

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
}