package com.bcnc.ecommerceprices.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DisplayName("Price Domain Model Tests")
class PriceTest {

    private final Brand testBrand = new Brand(1L, "Test Brand");

    @Test
    @DisplayName("should apply when application date is within start and end date")
    void appliesTo_whenDateIsWithinRange_shouldReturnTrue() {
        LocalDateTime startDate = LocalDateTime.of(2020, 1, 1, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 1, 31, 23, 59, 59);
        Price price = new Price(this.testBrand, startDate, endDate, 1, 100L, 0, 10.0, "USD");

        LocalDateTime applicationDate = LocalDateTime.of(2020, 1, 15, 12, 0, 0);
        assertTrue(price.appliesTo(applicationDate));
    }

    @Test
    @DisplayName("should apply when application date is exactly start date")
    void appliesTo_whenDateIsStartDate_shouldReturnTrue() {
        LocalDateTime startDate = LocalDateTime.of(2020, 1, 1, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 1, 31, 23, 59, 59);
        Price price = new Price(this.testBrand, startDate, endDate, 1, 100L, 0, 10.0, "USD");

        LocalDateTime applicationDate = LocalDateTime.of(2020, 1, 1, 0, 0, 0);
        assertTrue(price.appliesTo(applicationDate));
    }

    @Test
    @DisplayName("should apply when application date is exactly end date")
    void appliesTo_whenDateIsEndDate_shouldReturnTrue() {
        LocalDateTime startDate = LocalDateTime.of(2020, 1, 1, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 1, 31, 23, 59, 59);
        Price price = new Price(this.testBrand, startDate, endDate, 1, 100L, 0, 10.0, "USD");

        LocalDateTime applicationDate = LocalDateTime.of(2020, 1, 31, 23, 59, 59);
        assertTrue(price.appliesTo(applicationDate));
    }

    @Test
    @DisplayName("should not apply when application date is before start date")
    void appliesTo_whenDateIsBeforeRange_shouldReturnFalse() {
        LocalDateTime startDate = LocalDateTime.of(2020, 1, 1, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 1, 31, 23, 59, 59);
        Price price = new Price(this.testBrand, startDate, endDate, 1, 100L, 0, 10.0, "USD");

        LocalDateTime applicationDate = LocalDateTime.of(2019, 12, 31, 23, 59, 59);
        assertFalse(price.appliesTo(applicationDate));
    }

    @Test
    @DisplayName("should not apply when application date is after end date")
    void appliesTo_whenDateIsAfterRange_shouldReturnFalse() {
        LocalDateTime startDate = LocalDateTime.of(2020, 1, 1, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 1, 31, 23, 59, 59);
        Price price = new Price(this.testBrand, startDate, endDate, 1, 100L, 0, 10.0, "USD");

        LocalDateTime applicationDate = LocalDateTime.of(2020, 2, 1, 0, 0, 0);
        assertFalse(price.appliesTo(applicationDate));
    }

    @Test
    @DisplayName("equals and hashCode should work correctly")
    void equalsAndHashCode_shouldWorkCorrectly() {
        LocalDateTime startDate = LocalDateTime.of(2020, 1, 1, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 1, 31, 23, 59, 59);

        Price price1 = new Price(this.testBrand, startDate, endDate, 1, 100L, 0, 10.0, "USD");
        Price price2 = new Price(this.testBrand, startDate, endDate, 1, 100L, 0, 10.0, "USD");
        Price price3 = new Price(new Brand(2L, "Other Brand"), startDate, endDate, 1, 100L, 0, 10.0, "USD");
        Price price4 = new Price(this.testBrand, startDate.plusDays(1), endDate, 1, 100L, 0, 10.0, "USD");

        assertEquals(price1, price2);
        assertEquals(price1.hashCode(), price2.hashCode());
        assertNotEquals(price1, price3);
        assertNotEquals(price1.hashCode(), price3.hashCode());
        assertNotEquals(price1, price4);
        assertNotEquals(price1.hashCode(), price4.hashCode());
    }

    @Test
    @DisplayName("toString should return expected format")
    void toString_shouldReturnExpectedFormat() {
        LocalDateTime startDate = LocalDateTime.of(2020, 1, 1, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 1, 31, 23, 59, 59);
        Price price = new Price(this.testBrand, startDate, endDate, 1, 100L, 0, 10.0, "USD");

        String expected = "Price{brand=Brand{id=1, name='Test Brand'}, startDate=2020-01-01T00:00, endDate=2020-01-31T23:59:59, priceList=1, productId=100, priority=0, price=10.0, currency='USD'}";
        assertEquals(expected, price.toString());
    }
}