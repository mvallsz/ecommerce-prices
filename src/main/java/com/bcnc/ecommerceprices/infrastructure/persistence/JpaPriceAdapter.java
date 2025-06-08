package com.bcnc.ecommerceprices.infrastructure.persistence;

import com.bcnc.ecommerceprices.domain.model.Brand;
import com.bcnc.ecommerceprices.domain.model.Price;
import com.bcnc.ecommerceprices.domain.port.PriceRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class JpaPriceAdapter implements PriceRepository {

    private final SpringDataPriceRepository springDataPriceRepository;

    public JpaPriceAdapter(SpringDataPriceRepository springDataPriceRepository) {
        this.springDataPriceRepository = springDataPriceRepository;
    }

    @Override
    public Optional<Price> findApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId) {
        return this.springDataPriceRepository.findTopApplicablePriceEntity(applicationDate, productId, brandId)
                .stream()
                .findFirst()
                .map(this::toDomainModel);
    }

    private Price toDomainModel(PriceEntity entity) {
        Brand brand = null;
        if (entity.getBrand() != null) {
            brand = new Brand(entity.getBrand().getId(), entity.getBrand().getName());
        }

        return new Price(
                brand,
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getPriceList(),
                entity.getProductId(),
                entity.getPriority(),
                entity.getPrice(),
                entity.getCurr()
        );
    }
}