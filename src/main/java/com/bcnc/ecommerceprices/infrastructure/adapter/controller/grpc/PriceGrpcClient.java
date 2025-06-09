package com.bcnc.ecommerceprices.infrastructure.adapter.controller.grpc;

import com.bcnc.ecommerceprices.grpc.PriceGrpcServiceGrpc;
import com.bcnc.ecommerceprices.grpc.PriceRequest;
import com.bcnc.ecommerceprices.grpc.PriceResponse;
import com.google.protobuf.Timestamp;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * gRPC client for interacting with the PriceGrpcService.
 * Acts as an adapter between the domain layer and external gRPC services.
 */
@Component
public class PriceGrpcClient {

    private final ManagedChannel channel;
    private final PriceGrpcServiceGrpc.PriceGrpcServiceBlockingStub stub;
    private final PriceGrpcServiceImpl priceService;

    /**
     * Constructor for dependency injection.
     *
     * @param priceService the domain service for price-related operations
     */
    public PriceGrpcClient(PriceGrpcServiceImpl priceService) {
        this.channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext() // Use plaintext for local testing
                .build();
        this.stub = PriceGrpcServiceGrpc.newBlockingStub(channel);
        this.priceService = priceService;
    }

    /**
     * Retrieves the applicable price using the gRPC service.
     *
     * @param applicationDate the date for which the price is applicable
     * @param productId       the ID of the product
     * @param brandId         the ID of the brand
     * @return the applicable price response
     */
    public PriceResponse getApplicablePrice(String applicationDate, Long productId, Long brandId) {
        try {
            // Build the gRPC request
            Timestamp timestamp = Timestamp.newBuilder()
                    .setSeconds(Instant.parse(applicationDate).getEpochSecond())
                    .build();

            PriceRequest request = PriceRequest.newBuilder()
                    .setApplicationDate(timestamp)
                    .setProductId(productId)
                    .setBrandId(brandId)
                    .build();

            // Call the gRPC service
            return stub.getApplicablePrice(request);
        } catch (Exception e) {
            throw new RuntimeException("Error calling gRPC service: " + e.getMessage(), e);
        }
    }

    /**
     * Shuts down the gRPC channel.
     */
    public void shutdown() {
        channel.shutdown();
    }
}