package com.bcnc.ecommerceprices.infrastructure.adapter.grpc;

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

@GrpcService // Anotación de net.devh para que Spring detecte este servicio gRPC
public class PriceGrpcServiceImpl extends PriceGrpcServiceGrpc.PriceGrpcServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(PriceGrpcServiceImpl.class);

    private final PriceQueryService priceQueryService;

    public PriceGrpcServiceImpl(PriceQueryService priceQueryService) {
        this.priceQueryService = priceQueryService;
    }

    @Override
    public void getApplicablePrice(PriceRequest request, StreamObserver<PriceResponse> responseObserver) {
        log.info("Received gRPC request for product_id: {}, brand_id: {}, application_date: {}",
                request.getProductId(), request.getBrandId(), request.getApplicationDate());

        try {
            // Convertir Timestamp de Protobuf a LocalDateTime
            LocalDateTime applicationDate = convertTimestampToLocalDateTime(request.getApplicationDate());

            // Llamar al servicio de aplicación del dominio
            Price domainPrice = priceQueryService.getFinalApplicablePrice(
                    applicationDate,
                    request.getProductId(),
                    request.getBrandId()
            );

            // Mapear el modelo de dominio a la respuesta gRPC
            PriceResponse response = mapToGrpcPriceResponse(domainPrice);

            // Enviar la respuesta
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            log.info("gRPC response sent successfully for product_id: {}", request.getProductId());

        } catch (PriceNotFoundException e) {
            log.warn("Price not found for gRPC request: {}", e.getMessage());
            // Manejar el error de negocio y enviarlo al cliente gRPC
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription(e.getMessage())
                    .asRuntimeException());
        } catch (Exception e) {
            log.error("An unexpected error occurred during gRPC call: {}", e.getMessage(), e);
            // Manejar otros errores inesperados
            responseObserver.onError(Status.INTERNAL
                    .withDescription("An internal server error occurred: " + e.getMessage())
                    .asRuntimeException());
        }
    }

    // --- Métodos de Mapeo y Utilidad ---

    private LocalDateTime convertTimestampToLocalDateTime(Timestamp timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos()), ZoneOffset.UTC);
    }

    private Timestamp convertLocalDateTimeToTimestamp(LocalDateTime localDateTime) {
        Instant instant = localDateTime.toInstant(ZoneOffset.UTC);
        return Timestamp.newBuilder()
                .setSeconds(instant.getEpochSecond())
                .setNanos(instant.getNano())
                .build();
    }

    private PriceResponse mapToGrpcPriceResponse(Price domainPrice) {
        return PriceResponse.newBuilder()
                .setProductId(domainPrice.getProductId())
                .setBrandId(domainPrice.getBrand().getId())
                .setPriceList(domainPrice.getPriceList())
                .setStartDate(convertLocalDateTimeToTimestamp(domainPrice.getStartDate()))
                .setEndDate(convertLocalDateTimeToTimestamp(domainPrice.getEndDate()))
                .setFinalPrice(domainPrice.getPrice())
                .setCurrency(domainPrice.getCurr())
                .build();
    }
}