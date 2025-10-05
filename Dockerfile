# downloading light weight java17
FROM eclipse-temurin:17-jdk-alpine

# metadata of container
LABEL maintainer="shubhamnigam@gmail.com"
LABEL version="1.0"docker build --platform linux/amd64 -t demo-0.0.1-snapshot .
LABEL description="A Simple Java SpringBoot MySQL App"

# create working directory
WORKDIR /app

# copy sourcecode which is present in host copy to the container
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]