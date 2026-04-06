# Stage 1: Build the jar
FROM maven:3.9.0-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

RUN mvn clean package -DskipTests

# Stage 2: Run the app
FROM eclipse-temurin:17-jdk
WORKDIR /app

COPY --from=build /app/target/shopzy-0.0.1-SNAPSHOT.jar app.jar

ENV PORT=8080
EXPOSE 8080

# Run app with dynamic port support
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT}"]