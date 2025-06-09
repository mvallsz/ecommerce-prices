package com.bcnc.ecommerceprices.infrastructure.persistence;

import com.bcnc.ecommerceprices.domain.model.Brand;
import com.bcnc.ecommerceprices.domain.model.Price;
import com.bcnc.ecommerceprices.domain.port.PriceRepository;
import com.bcnc.ecommerceprices.infrastructure.persistence.entity.PriceEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Adapter for interacting with the persistence layer.
 * Implements the PriceRepository port to provide domain-specific operations.
 * Acts as a bridge between the domain and infrastructure layers.
 */
@Component
public class PricePersistenceAdapter implements PriceRepository {

    private final SpringDataPriceRepository springDataPriceRepository;

    /**
     * Constructor for dependency injection.
     *
     * @param springDataPriceRepository the repository for accessing price entities
     */
    public PricePersistenceAdapter(SpringDataPriceRepository springDataPriceRepository) {
        this.springDataPriceRepository = springDataPriceRepository;
    }

    /**
     * Finds the applicable price based on the application date, product ID, and brand ID.
     *
     * @param applicationDate the date for which the price is applicable
     * @param productId       the ID of the product
     * @param brandId         the ID of the brand
     * @return an Optional containing the applicable Price domain model, if found
     */
    @Override
    public Optional<Price> findApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId) {
        return this.springDataPriceRepository.findApplicablePriceEntities(applicationDate, productId, brandId)
                .stream()
                .findFirst()
                .map(this::toDomainModel);
    }

    /**
     * Converts a PriceEntity to a Price domain model.
     *
     * @param entity the PriceEntity from the persistence layer
     * @return the Price domain model
     */
    private Price toDomainModel(PriceEntity entity) {
        Brand brand = Optional.ofNullable(entity.getBrand())
                .map(b -> new Brand(b.getId(), b.getName()))
                .orElse(null);

        return new Price(
                brand,
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getPriceList(),
                entity.getProductId(),
                entity.getPriority(),
                entity.getPrice(),
                entity.getCurrency()
        );
    }
}