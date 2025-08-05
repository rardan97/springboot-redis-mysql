#Build
FROM maven:3.9.11 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

#Runtime
FROM eclipse-temurin:17-jdk-alpine

# Install Redis
RUN apk add --no-cache redis

WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Copy running.sh
COPY running.sh /app/running.sh
RUN chmod +x /app/running.sh

EXPOSE 8080
#
## Use running.sh as entrypoint
ENTRYPOINT ["java", "-Xms512m", "-Xmx512m", "-jar", "/app/app.jar"]