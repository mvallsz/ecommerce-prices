package com.bcnc.ecommerceprices.infrastructure.adapter.controller.web;

import com.bcnc.ecommerceprices.domain.model.Price;
import com.bcnc.ecommerceprices.infrastructure.adapter.controller.web.dto.PriceResponse;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting Price domain entities to DTOs.
 */
@Component
public class PriceMapper {

    /**
     * Maps a Price domain entity to a PriceResponse DTO.
     *
     * @param price the Price entity
     * @return the PriceResponse DTO
     */
    public PriceResponse toResponse(Price price) {
        return new PriceResponse(
                price.getProductId(),
                price.getBrand().getId(),
                price.getPriceList(),
                price.getStartDate(),
                price.getEndDate(),
                price.getPrice(),
                price.getCurrency()
        );
    }
}
