package com.bcnc.ecommerceprices.infrastructure.adapter.controller;

import com.bcnc.ecommerceprices.application.PriceQueryService;
import com.bcnc.ecommerceprices.domain.exception.PriceNotFoundException;
import com.bcnc.ecommerceprices.domain.model.Price;
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

@RestController
@RequestMapping("/api/prices")
public class PriceController {

    private final PriceQueryService priceQueryService;

    public PriceController(PriceQueryService priceQueryService) {
        this.priceQueryService = priceQueryService;
    }

    @GetMapping("/applicable")
    public ResponseEntity<PriceResponse> getApplicablePrice(
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd-HH.mm.ss") LocalDateTime applicationDate,
            @RequestParam("product_id") Long productId,
            @RequestParam("brand_id") Long brandId) {

        // El servicio de aplicación lanzará PriceNotFoundException si no se encuentra
        Price price = this.priceQueryService.getFinalApplicablePrice(applicationDate, productId, brandId);
        return new ResponseEntity<>(this.mapToPriceResponse(price), HttpStatus.OK);
    }

    // Manejador de excepciones
    @ExceptionHandler(PriceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // Devuelve 404
    public PriceErrorResponse handlePriceNotFoundException(PriceNotFoundException ex, HttpServletRequest request) {
        return new PriceErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    private PriceResponse mapToPriceResponse(Price price) {
        return new PriceResponse(
                price.getProductId(),
                price.getBrand().getId(),
                price.getPriceList(),
                price.getStartDate(),
                price.getEndDate(),
                price.getPrice(),
                price.getCurr()
        );
    }
}