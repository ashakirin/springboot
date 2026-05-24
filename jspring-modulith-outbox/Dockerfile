# syntax=docker/dockerfile:1
# ---- build stage ---------------------------------------------------------
FROM maven:3.9-eclipse-temurin-25 AS build
WORKDIR /workspace
COPY pom.xml .
RUN mvn -B -q dependency:go-offline
COPY src src
RUN mvn -B -q -DskipTests package

# ---- runtime stage -------------------------------------------------------
FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=build /workspace/target/order-service-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
