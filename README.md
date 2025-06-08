# Nombre del Proyecto:
`ecommerce-prices` (Servicio de Precios gRPC/Web)

Este proyecto es un servicio de precios que expone APIs tanto a través de gRPC como de servicios web HTTP/REST. Permite consultar precios de productos considerando la fecha de aplicación y la marca.

-----

## 🚀 Tecnologías Utilizadas

* **Java 17**
* **Spring Boot 3.x**
* **gRPC** (con `net.devh` Spring Boot gRPC Starter)
* **Maven**
* **Docker**
* **Kubernetes** (Despliegue en Azure Kubernetes Service - AKS)
* **GitHub Actions** (CI/CD Pipeline)

-----

## 🛠️ Construcción Local

Para construir el proyecto localmente, asegúrate de tener Maven y Java 17 instalados.

1.  **Clona el repositorio:**

    ```bash
    git clone https://github.com/mvallsz/ecommerce-prices.git
    cd ecommerce-prices
    ```

2.  **Compila el proyecto:**

    ```bash
    mvn clean install
    ```

    Esto generará el archivo JAR ejecutable en la carpeta `target/`.

3.  **Ejecuta la aplicación:**

    ```bash
    java -jar target/ecommerce-prices-0.0.1-SNAPSHOT.jar # Ajusta el nombre del JAR si es diferente
    ```

    La aplicación se iniciará, exponiendo el servicio gRPC en el puerto `9090` y el servicio web en el puerto `8080`.

-----

## 🐳 Dockerización

Para construir una imagen Docker de la aplicación, utiliza el `Dockerfile` proporcionado.

1.  **Asegúrate de tener Docker instalado.**

2.  **Construye la imagen Docker:**

    ```bash
    docker build -t ecommerce-prices:latest .
    ```

3.  **Ejecuta el contenedor Docker (opcional, para pruebas locales):**

    ```bash
    docker run -p 9090:9090 -p 8080:8080 --name ecommerce-prices-app ecommerce-prices:latest
    ```

    Ahora puedes acceder al servicio gRPC en `localhost:9090` y al servicio web en `localhost:8080`.

-----

## 🚀 Despliegue en Kubernetes (AKS)

Este proyecto está configurado para ser desplegado en un clúster de Kubernetes.

### 📁 Archivos de Configuración de Kubernetes

Los manifiestos de Kubernetes se encuentran en la carpeta `kubernetes/`:

* `kubernetes/web-deployment.yaml`: Define el **despliegue web** de la aplicación, especificando las réplicas (inicialmente 3 para multiconcurrencia), los recursos (CPU/memoria) y la imagen Docker.
* * `kubernetes/grpc-deployment.yaml`: Define el **despliegue grpc** de la aplicación, especificando las réplicas (inicialmente 3 para multiconcurrencia), los recursos (CPU/memoria) y la imagen Docker.
* `kubernetes/web-service.yaml && kubernetes/grpc-service.yaml`: Define **dos servicios** para exponer los diferentes tipos de tráfico de la aplicación:
    * **Puerto 9090 (gRPC):** Expuesto como un `LoadBalancer` para acceso externo directo a gRPC (o `ClusterIP` si se usa un Ingress compatible con HTTP/2).
    * **Puerto 8080 (HTTP/Web):** Expuesto como un `LoadBalancer` para el tráfico web (o `ClusterIP` si se usa un Ingress HTTP/HTTPS estándar).
    * **Nota sobre puertos aislados:** Los puertos gRPC y web están aislados para permitir una **escalabilidad independiente** y un mejor balanceo de carga para cada tipo de tráfico.
* `kubernetes/web-hpa.yaml && kubernetes/grpc-hpa.yaml`: Configura un **Horizontal Pod Autoscaler (HPA)** para escalar automáticamente las réplicas del despliegue basándose en el uso de CPU (escalará entre 3 y 10 réplicas cuando el uso de CPU exceda el 70%).

**Asegúrate de actualizar los placeholders** en `kubernetes/deployment.yaml` con el nombre de tu Azure Container Registry (ACR), por ejemplo: `youracrname.azurecr.io/ecommerce-prices:latest`.

-----

## 📞 Uso de los Servicios

Una vez desplegado en Kubernetes:

* **Acceso gRPC:** Podrás conectarte al servicio gRPC usando la IP pública del `Service` `ecommerce-prices-grpc-service` en el puerto `9090`.
* **Acceso Web:** Podrás acceder al servicio web usando la IP pública del `Service` `ecommerce-prices-web-service` en el puerto `80` (o `443` si configuras HTTPS en un Ingress).

-----

## Endpoint de Consulta de Precios

El servicio expone un endpoint GET para consultar el precio aplicable:

### GET `/api/prices/applicable`

**Parámetros de Consulta (Query Parameters):**

* `date`: Fecha y hora de aplicación del precio (formato: `yyyy-MM-dd-HH.mm.ss`). **Obligatorio.**
* `product_id`: Identificador del producto (ej: `35455`). **Obligatorio.**
* `brand_id`: Identificador de la cadena (ej: `1` para ZARA). **Obligatorio.**

**Ejemplo de Petición Exitosa (HTTP 200 OK):**

```http
GET http://localhost:8080/api/prices/applicable?date=2020-06-14-16.00.00&product_id=35455&brand_id=1
```

## Nuevo Puerto gRPC

Además de la API REST, la aplicación ahora expone un servicio gRPC para la consulta de precios, ofreciendo una alternativa de comunicación eficiente y con tipado fuerte.

### Definición del Servicio (price_service.proto)

El servicio gRPC se define en `src/main/proto/price_service.proto`:

```protobuf
// Contenido del price_service.proto
syntax = "proto3";

option java_package = "com.example.ecommerceprices.grpc";
option java_multiple_files = true;

import "google/protobuf/timestamp.proto";

package ecommerceprices;

service PriceGrpcService {
  rpc GetApplicablePrice (PriceRequest) returns (PriceResponse);
}

message PriceRequest {
  google.protobuf.Timestamp application_date = 1;
  int64 product_id = 2;
  int64 brand_id = 3;
}

message PriceResponse {
  int64 product_id = 1;
  int64 brand_id = 2;
  int32 price_list = 3;
  google.protobuf.Timestamp start_date = 4;
  google.protobuf.Timestamp end_date = 5;
  double final_price = 6;
  string currency = 7;
}
