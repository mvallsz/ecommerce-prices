package com.bcnc.ecommerceprices.infrastructure.persistence;

import com.bcnc.ecommerceprices.infrastructure.persistence.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for accessing PriceEntity data in the database.
 * Provides methods for querying price-related information.
 */
@Repository
public interface SpringDataPriceRepository extends JpaRepository<PriceEntity, Long> {

    /**
     * Finds the applicable price entity based on the application date, product ID, and brand ID.
     * Order results by priority in descending order.
     *
     * @param applicationDate the date for which the price is applicable
     * @param productId       the ID of the product
     * @param brandId         the ID of the brand
     * @return a list of PriceEntity objects matching the criteria, ordered by priority
     */
    @Query(value = "SELECT p FROM PriceEntity p WHERE p.productId = :productId AND p.brand.id = :brandId AND :applicationDate BETWEEN p.startDate AND p.endDate ORDER BY p.priority DESC")
    List<PriceEntity> findApplicablePriceEntities(
            @Param("applicationDate") LocalDateTime applicationDate,
            @Param("productId") Long productId,
            @Param("brandId") Long brandId);
}