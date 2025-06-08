package com.bcnc.ecommerceprices.infrastructure.adapter.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PriceControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getBaseUrl() {
        return "http://localhost:" + this.port + "/api/prices/applicable";
    }

    // Test 1: petición a las 10:00 del día 14 del producto 35455 para la brand 1 (ZARA)
    @Test
    void testGetApplicablePrice_Test1() {
        String url = this.getBaseUrl() + "?date=2020-06-14-10.00.00&product_id=35455&brand_id=1";
        ResponseEntity<PriceResponse> response = this.restTemplate.getForEntity(url, PriceResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(35455L, response.getBody().getProductId());
        assertEquals(1L, response.getBody().getBrandId());
        assertEquals(1, response.getBody().getPriceList());
        assertEquals("2020-06-14 00.00.00", response.getBody().getStartDate().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH.mm.ss")));
        assertEquals("2020-12-31 23.59.59", response.getBody().getEndDate().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH.mm.ss")));
        assertEquals(35.50, response.getBody().getFinalPrice(), 0.001);
        assertEquals("EUR", response.getBody().getCurrency());
    }

    // Test 2: petición a las 16:00 del día 14 del producto 35455 para la brand 1 (ZARA)
    @Test
    void testGetApplicablePrice_Test2() {
        String url = this.getBaseUrl() + "?date=2020-06-14-16.00.00&product_id=35455&brand_id=1";
        ResponseEntity<PriceResponse> response = this.restTemplate.getForEntity(url, PriceResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(35455L, response.getBody().getProductId());
        assertEquals(1L, response.getBody().getBrandId());
        assertEquals(2, response.getBody().getPriceList());
        assertEquals("2020-06-14 15.00.00", response.getBody().getStartDate().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH.mm.ss")));
        assertEquals("2020-06-14 18.30.00", response.getBody().getEndDate().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH.mm.ss")));
        assertEquals(25.45, response.getBody().getFinalPrice(), 0.001);
        assertEquals("EUR", response.getBody().getCurrency());
    }

    // Test 3: petición a las 21:00 del día 14 del producto 35455 para la brand 1 (ZARA)
    @Test
    void testGetApplicablePrice_Test3() {
        String url = this.getBaseUrl() + "?date=2020-06-14-21.00.00&product_id=35455&brand_id=1";
        ResponseEntity<PriceResponse> response = this.restTemplate.getForEntity(url, PriceResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(35455L, response.getBody().getProductId());
        assertEquals(1L, response.getBody().getBrandId());
        assertEquals(1, response.getBody().getPriceList());
        assertEquals("2020-06-14 00.00.00", response.getBody().getStartDate().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH.mm.ss")));
        assertEquals("2020-12-31 23.59.59", response.getBody().getEndDate().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH.mm.ss")));
        assertEquals(35.50, response.getBody().getFinalPrice(), 0.001);
        assertEquals("EUR", response.getBody().getCurrency());
    }

    // Test 4: petición a las 10:00 del día 15 del producto 35455 para la brand 1 (ZARA)
    @Test
    void testGetApplicablePrice_Test4() {
        String url = this.getBaseUrl() + "?date=2020-06-15-10.00.00&product_id=35455&brand_id=1";
        ResponseEntity<PriceResponse> response = this.restTemplate.getForEntity(url, PriceResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(35455L, response.getBody().getProductId());
        assertEquals(1L, response.getBody().getBrandId());
        assertEquals(3, response.getBody().getPriceList());
        assertEquals("2020-06-15 00.00.00", response.getBody().getStartDate().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH.mm.ss")));
        assertEquals("2020-06-15 11.00.00", response.getBody().getEndDate().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH.mm.ss")));
        assertEquals(30.50, response.getBody().getFinalPrice(), 0.001);
        assertEquals("EUR", response.getBody().getCurrency());
    }

    // Test 5: petición a las 21:00 del día 16 del producto 35455 para la brand 1 (ZARA)
    @Test
    void testGetApplicablePrice_Test5() {
        String url = this.getBaseUrl() + "?date=2020-06-16-21.00.00&product_id=35455&brand_id=1";
        ResponseEntity<PriceResponse> response = this.restTemplate.getForEntity(url, PriceResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(35455L, response.getBody().getProductId());
        assertEquals(1L, response.getBody().getBrandId());
        assertEquals(4, response.getBody().getPriceList());
        assertEquals("2020-06-15 16.00.00", response.getBody().getStartDate().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH.mm.ss")));
        assertEquals("2020-12-31 23.59.59", response.getBody().getEndDate().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH.mm.ss")));
        assertEquals(38.95, response.getBody().getFinalPrice(), 0.001);
        assertEquals("EUR", response.getBody().getCurrency());
    }

    // Test para un caso donde no se encuentre precio (ahora espera un 404 con un cuerpo de error)
    @Test
    void testGetApplicablePrice_NotFound() {
        String url = this.getBaseUrl() + "?date=2025-01-01-00.00.00&product_id=99999&brand_id=1";
        ResponseEntity<PriceErrorResponse> response = this.restTemplate.getForEntity(url, PriceErrorResponse.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getStatus());
        assertEquals(HttpStatus.NOT_FOUND.getReasonPhrase(), response.getBody().getError());
        assertNotNull(response.getBody().getMessage());
        assertEquals("/api/prices/applicable", response.getBody().getPath());
    }
}