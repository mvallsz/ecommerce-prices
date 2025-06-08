# Stage 1: Build the Spring Boot application
FROM eclipse-temurin:17-jdk-focal AS build
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY src/ ./src
RUN ./mvnw clean package -DskipTests

# Stage 2: Create the final image
FROM eclipse-temurin:17-jre-focal
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 9090 # Puerto donde escucha el servicio gRPC
EXPOSE 8080 # Puerto donde escucha el servicio HTTP

ENTRYPOINT ["java","-jar","app.jar"]