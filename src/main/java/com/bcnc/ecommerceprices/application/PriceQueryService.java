package com.bcnc.ecommerceprices.application;

import com.bcnc.ecommerceprices.domain.exception.PriceNotFoundException;
import com.bcnc.ecommerceprices.domain.model.Price;
import com.bcnc.ecommerceprices.domain.port.PriceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PriceQueryService {

    private final PriceRepository priceRepository;

    public PriceQueryService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public Price getFinalApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId) {
        return this.priceRepository.findApplicablePrice(applicationDate, productId, brandId)
                .orElseThrow(() -> new PriceNotFoundException(
                        "No price found for product " + productId +
                                ", brand " + brandId +
                                " at " + applicationDate));
    }
}