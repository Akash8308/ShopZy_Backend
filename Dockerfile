# Stage 1: Build the jar
FROM maven:3.9.0-eclipse-temurin-17 AS build
WORKDIR /app

# Copy Maven files first (for caching)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Build jar without tests
RUN mvn clean package -DskipTests

# Stage 2: Run the app
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copy correct jar (IMPORTANT FIX)
COPY --from=build /app/target/shopzy-0.0.1-SNAPSHOT.jar app.jar

# Railway uses dynamic port → use env variable
ENV PORT=8080
EXPOSE 8080

# Run app with dynamic port support
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT}"]