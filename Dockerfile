# Use Maven with OpenJDK 17 for the build stage
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Use a lightweight JDK 17 image for the final runtime
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/tapntrack_-0.0.1-SNAPSHOT.jar tapntrack_.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "tapntrack_.jar"]
