package com.bcnc.ecommerceprices.domain.port;

import com.bcnc.ecommerceprices.domain.model.Price;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Port interface for querying applicable prices.
 * Defines the contract for persistence operations related to prices.
 */
public interface PriceRepository {

    /**
     * Finds the applicable price for a given product, brand, and application date.
     *
     * @param applicationDate the date for which the price is applicable
     * @param productId the ID of the product
     * @param brandId the ID of the brand
     * @return an Optional containing the applicable price, or empty if no price is found
     */
    Optional<Price> findApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId);
}