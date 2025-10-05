# Use lightweight Java 17
FROM eclipse-temurin:17-jdk-alpine

LABEL maintainer="shubhamnigam@gmail.com"
LABEL version="1.0"
LABEL description="A Simple Java SpringBoot MySQL App"

WORKDIR /app

# Copy the latest JAR from target
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

# Optional: verify the JAR exists
RUN ls -lh /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]