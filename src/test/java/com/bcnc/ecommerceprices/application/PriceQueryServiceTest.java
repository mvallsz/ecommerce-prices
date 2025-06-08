package com.bcnc.ecommerceprices.application;

import com.bcnc.ecommerceprices.domain.exception.PriceNotFoundException;
import com.bcnc.ecommerceprices.domain.model.Brand;
import com.bcnc.ecommerceprices.domain.model.Price;
import com.bcnc.ecommerceprices.domain.port.PriceRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@DisplayName("PriceQueryService Unit Tests")
class PriceQueryServiceTest {

    @Mock
    private PriceRepository priceRepository; // Se mockea la interfaz del puerto

    @InjectMocks
    private PriceQueryService priceQueryService; // El servicio bajo prueba

    private final LocalDateTime testDate = LocalDateTime.of(2020, 6, 14, 10, 0, 0);
    private final Long testProductId = 35455L;
    private final Long testBrandId = 1L;
    private final Brand testBrand = new Brand(this.testBrandId, "ZARA");

    @Test
    @DisplayName("should return applicable price when found")
    void getFinalApplicablePrice_whenPriceFound_shouldReturnPrice() {
        // Given
        Price expectedPrice = new Price(this.testBrand, LocalDateTime.of(2020, 6, 14, 0, 0, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59, 59), 1, this.testProductId, 0, 35.50, "EUR");

        // Usar eq() para especificar los argumentos esperados al mock
        when(this.priceRepository.findApplicablePrice(eq(this.testDate), eq(this.testProductId), eq(this.testBrandId)))
                .thenReturn(Optional.of(expectedPrice));

        // When
        Price actualPrice = this.priceQueryService.getFinalApplicablePrice(this.testDate, this.testProductId, this.testBrandId);

        // Then
        assertEquals(expectedPrice, actualPrice);
        assertEquals(expectedPrice.getPrice(), actualPrice.getPrice());
        assertEquals(expectedPrice.getPriceList(), actualPrice.getPriceList());
    }

    @Test
    @DisplayName("should throw PriceNotFoundException when no price is found")
    void getFinalApplicablePrice_whenPriceNotFound_shouldThrowException() {
        // Given
        when(this.priceRepository.findApplicablePrice(eq(this.testDate), eq(this.testProductId), eq(this.testBrandId)))
                .thenReturn(Optional.empty());

        // When & Then
        PriceNotFoundException thrown = assertThrows(PriceNotFoundException.class, () ->
                this.priceQueryService.getFinalApplicablePrice(this.testDate, this.testProductId, this.testBrandId)
        );

        assertEquals("No price found for product 35455, brand 1 at 2020-06-14T10:00", thrown.getMessage());
    }
}