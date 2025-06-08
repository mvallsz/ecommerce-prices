package com.bcnc.ecommerceprices.infrastructure.adapter.grpc;

import com.bcnc.ecommerceprices.grpc.PriceGrpcServiceGrpc;
import com.bcnc.ecommerceprices.grpc.PriceRequest;
import com.bcnc.ecommerceprices.grpc.PriceResponse;
import com.google.protobuf.Timestamp;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.time.Instant;

public class PriceGrpcClient {

    public static void main(String[] args) {
        // Crear un canal gRPC
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext() // Usar texto plano para pruebas locales
                .build();

        // Crear un stub para el servicio
        PriceGrpcServiceGrpc.PriceGrpcServiceBlockingStub stub = PriceGrpcServiceGrpc.newBlockingStub(channel);

        // Construir la solicitud
        Timestamp applicationDate = Timestamp.newBuilder()
                .setSeconds(Instant.parse("2020-06-14T10:00:00Z").getEpochSecond())
                .build();

        PriceRequest request = PriceRequest.newBuilder()
                .setApplicationDate(applicationDate)
                .setProductId(35455)
                .setBrandId(1)
                .build();

        // Llamar al servicio y obtener la respuesta
        try {
            PriceResponse response = stub.getApplicablePrice(request);
            System.out.println("Response: " + response);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            // Cerrar el canal
            channel.shutdown();
        }
    }
}