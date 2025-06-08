package com.bcnc.ecommerceprices.infrastructure.adapter.grpc;

import com.bcnc.ecommerceprices.grpc.PriceGrpcServiceGrpc;
import com.bcnc.ecommerceprices.grpc.PriceRequest;
import com.bcnc.ecommerceprices.grpc.PriceResponse;
import com.google.protobuf.Timestamp;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class PriceGrpcServiceIntegrationTest {

    private ManagedChannel channel;
    private PriceGrpcServiceGrpc.PriceGrpcServiceBlockingStub stub;

    @BeforeEach
    void setUp() {
        // Configurar el canal gRPC
        channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();
        stub = PriceGrpcServiceGrpc.newBlockingStub(channel);
    }

    @AfterEach
    void tearDown() {
        // Cerrar el canal gRPC
        channel.shutdown();
    }

    @Test
    void testGetApplicablePrice_ValidRequest() {
        // Crear una solicitud válida
        Timestamp applicationDate = Timestamp.newBuilder()
                .setSeconds(Instant.parse("2020-06-14T10:00:00Z").getEpochSecond())
                .build();

        PriceRequest request = PriceRequest.newBuilder()
                .setApplicationDate(applicationDate)
                .setProductId(35455)
                .setBrandId(1)
                .build();

        // Llamar al servicio y validar la respuesta
        PriceResponse response = stub.getApplicablePrice(request);

        assertNotNull(response);
        assertEquals(35455L, response.getProductId());
        assertEquals(1L, response.getBrandId());
        assertEquals(1, response.getPriceList());
        assertEquals("EUR", response.getCurrency());
        assertEquals(35.50, response.getFinalPrice(), 0.001);
    }

    @Test
    void testGetApplicablePrice_NotFound() {
        // Crear una solicitud que no debería devolver resultados
        Timestamp applicationDate = Timestamp.newBuilder()
                .setSeconds(Instant.parse("2025-01-01T00:00:00Z").getEpochSecond())
                .build();

        PriceRequest request = PriceRequest.newBuilder()
                .setApplicationDate(applicationDate)
                .setProductId(99999)
                .setBrandId(1)
                .build();

        // Llamar al servicio y validar el manejo de errores
        Exception exception = assertThrows(StatusRuntimeException.class, () -> stub.getApplicablePrice(request));
        assertTrue(exception.getMessage().contains("NOT_FOUND"));
    }
}