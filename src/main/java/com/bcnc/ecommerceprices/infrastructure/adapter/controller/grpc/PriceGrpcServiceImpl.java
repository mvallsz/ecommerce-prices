package com.bcnc.ecommerceprices.infrastructure.adapter.controller.grpc;

import com.bcnc.ecommerceprices.application.PriceQueryService;
import com.bcnc.ecommerceprices.domain.exception.PriceNotFoundException;
import com.bcnc.ecommerceprices.domain.model.Price;
import com.bcnc.ecommerceprices.grpc.PriceGrpcServiceGrpc;
import com.bcnc.ecommerceprices.grpc.PriceRequest;
import com.bcnc.ecommerceprices.grpc.PriceResponse;
import com.google.protobuf.Timestamp;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * gRPC controller for handling price-related requests.
 * Acts as an adapter between the gRPC layer and the application layer.
 * Delegates business logic to the PriceQueryService.
 */
@GrpcService
public class PriceGrpcServiceImpl extends PriceGrpcServiceGrpc.PriceGrpcServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(PriceGrpcServiceImpl.class);

    private final PriceQueryService priceQueryService;

    /**
     * Constructor for dependency injection.
     *
     * @param priceQueryService the application service for querying prices
     */
    public PriceGrpcServiceImpl(PriceQueryService priceQueryService) {
        this.priceQueryService = priceQueryService;
    }

    /**
     * Handles gRPC requests to retrieve the applicable price.
     *
     * @param request          the gRPC request containing product, brand, and application date
     * @param responseObserver the gRPC response observer for sending the result
     */
    @Override
    public void getApplicablePrice(PriceRequest request, StreamObserver<PriceResponse> responseObserver) {
        log.info("Received gRPC request: product_id={}, brand_id={}, application_date={}",
                request.getProductId(), request.getBrandId(), request.getApplicationDate());

        try {
            // Validate and convert input parameters
            LocalDateTime applicationDate = convertTimestampToLocalDateTime(request.getApplicationDate());

            // Query the domain service for the applicable price
            Price domainPrice = priceQueryService.getFinalApplicablePrice(
                    applicationDate,
                    request.getProductId(),
                    request.getBrandId()
            );

            // Map the domain model to the gRPC response
            PriceResponse response = mapToGrpcPriceResponse(domainPrice);

            // Send the response
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            log.info("Successfully sent gRPC response for product_id={}", request.getProductId());

        } catch (PriceNotFoundException e) {
            log.warn("Price not found: {}", e.getMessage());
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription(e.getMessage())
                    .asRuntimeException());
        } catch (Exception e) {
            log.error("Unexpected error during gRPC call: {}", e.getMessage(), e);
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Internal server error: " + e.getMessage())
                    .asRuntimeException());
        }
    }

    /**
     * Converts a Protobuf Timestamp to LocalDateTime.
     *
     * @param timestamp the Protobuf Timestamp
     * @return the corresponding LocalDateTime
     */
    private LocalDateTime convertTimestampToLocalDateTime(Timestamp timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos()), ZoneOffset.UTC);
    }

    /**
     * Converts a LocalDateTime to Protobuf Timestamp.
     *
     * @param localDateTime the LocalDateTime
     * @return the corresponding Protobuf Timestamp
     */
    private Timestamp convertLocalDateTimeToTimestamp(LocalDateTime localDateTime) {
        Instant instant = localDateTime.toInstant(ZoneOffset.UTC);
        return Timestamp.newBuilder()
                .setSeconds(instant.getEpochSecond())
                .setNanos(instant.getNano())
                .build();
    }

    /**
     * Maps a domain Price model to a gRPC PriceResponse.
     *
     * @param domainPrice the domain Price model
     * @return the corresponding gRPC PriceResponse
     */
    private PriceResponse mapToGrpcPriceResponse(Price domainPrice) {
        return PriceResponse.newBuilder()
                .setProductId(domainPrice.getProductId())
                .setBrandId(domainPrice.getBrand().getId())
                .setPriceList(domainPrice.getPriceList())
                .setStartDate(convertLocalDateTimeToTimestamp(domainPrice.getStartDate()))
                .setEndDate(convertLocalDateTimeToTimestamp(domainPrice.getEndDate()))
                .setFinalPrice(domainPrice.getPrice())
                .setCurrency(domainPrice.getCurrency())
                .build();
    }
}