package com.bcnc.ecommerceprices.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SpringDataPriceRepository extends JpaRepository<PriceEntity, Long> {

    @Query(value = "SELECT p FROM PriceEntity p WHERE p.productId = :productId AND p.brand.id = :brandId AND :applicationDate BETWEEN p.startDate AND p.endDate ORDER BY p.priority DESC LIMIT 1")
    List<PriceEntity> findTopApplicablePriceEntity(
            @Param("applicationDate") LocalDateTime applicationDate,
            @Param("productId") Long productId,
            @Param("brandId") Long brandId);
}