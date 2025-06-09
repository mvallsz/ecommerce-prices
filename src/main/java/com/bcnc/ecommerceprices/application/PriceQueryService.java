package com.bcnc.ecommerceprices.application;

import com.bcnc.ecommerceprices.domain.exception.PriceNotFoundException;
import com.bcnc.ecommerceprices.domain.model.Price;
import com.bcnc.ecommerceprices.domain.port.PriceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Service class responsible for querying applicable prices.
 * This class is part of the application layer in a hexagonal architecture.
 */
@Service
public class PriceQueryService {

    private final PriceRepository priceRepository;

    /**
     * Constructor for PriceQueryService.
     *
     * @param priceRepository the repository used to query prices
     * @throws NullPointerException if the priceRepository is null
     */
    public PriceQueryService(PriceRepository priceRepository) {
        this.priceRepository = Objects.requireNonNull(priceRepository, "PriceRepository cannot be null");
    }

    /**
     * Retrieves the final applicable price for a given product, brand, and application date.
     *
     * @param applicationDate the date for which the price is applicable
     * @param productId the ID of the product
     * @param brandId the ID of the brand
     * @return the applicable price
     * @throws IllegalArgumentException if any of the input parameters are invalid
     * @throws PriceNotFoundException if no price is found for the given parameters
     */
    public Price getFinalApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId) {
        validateInputs(applicationDate, productId, brandId);
        return this.priceRepository.findApplicablePrice(applicationDate, productId, brandId)
                .orElseThrow(() -> new PriceNotFoundException(
                        "No price found for product " + productId +
                                ", brand " + brandId +
                                " at " + applicationDate));
    }

    /**
     * Validates the input parameters for querying prices.
     *
     * @param applicationDate the date for which the price is applicable
     * @param productId the ID of the product
     * @param brandId the ID of the brand
     * @throws IllegalArgumentException if any parameter is null or invalid
     */
    private void validateInputs(LocalDateTime applicationDate, Long productId, Long brandId) {
        if (applicationDate == null) {
            throw new IllegalArgumentException("Application date cannot be null");
        }
        if (productId == null || productId <= 0) {
            throw new IllegalArgumentException("Product ID must be a positive number");
        }
        if (brandId == null || brandId <= 0) {
            throw new IllegalArgumentException("Brand ID must be a positive number");
        }
    }
}