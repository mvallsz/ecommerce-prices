package com.bcnc.ecommerceprices.infrastructure.adapter.controller.web;

import com.bcnc.ecommerceprices.application.PriceQueryService;
import com.bcnc.ecommerceprices.domain.exception.PriceNotFoundException;
import com.bcnc.ecommerceprices.domain.model.Price;
import com.bcnc.ecommerceprices.infrastructure.adapter.controller.web.dto.PriceErrorResponse;
import com.bcnc.ecommerceprices.infrastructure.adapter.controller.web.dto.PriceResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * Controller for handling price-related HTTP requests.
 * Acts as an adapter in the infrastructure layer of a hexagonal architecture.
 */
@RestController
@RequestMapping("/api/prices")
public class PriceController {

    private final PriceQueryService priceQueryService;
    private final PriceMapper priceMapper;

    /**
     * Constructs a new PriceController.
     *
     * @param priceQueryService the service for querying prices
     * @param priceMapper       the mapper for converting domain entities to DTOs
     */
    public PriceController(PriceQueryService priceQueryService, PriceMapper priceMapper) {
        this.priceQueryService = priceQueryService;
        this.priceMapper = priceMapper;
    }

    /**
     * Retrieves the applicable price for a given product, brand, and application date.
     *
     * @param applicationDate the date for which the price is applicable
     * @param productId       the ID of the product
     * @param brandId         the ID of the brand
     * @return the applicable price wrapped in a ResponseEntity
     */
    @GetMapping("/applicable")
    public ResponseEntity<PriceResponse> getApplicablePrice(
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd-HH.mm.ss") LocalDateTime applicationDate,
            @RequestParam("product_id") Long productId,
            @RequestParam("brand_id") Long brandId) {

        Price price = priceQueryService.getFinalApplicablePrice(applicationDate, productId, brandId);
        return ResponseEntity.ok(priceMapper.toResponse(price));
    }

    /**
     * Handles PriceNotFoundException and returns a 404 response.
     *
     * @param ex      the exception thrown
     * @param request the HTTP request that caused the exception
     * @return the error response
     */
    @ExceptionHandler(PriceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public PriceErrorResponse handlePriceNotFoundException(PriceNotFoundException ex, HttpServletRequest request) {
        return new PriceErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );
    }
}