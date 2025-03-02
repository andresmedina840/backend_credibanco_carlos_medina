FROM eclipse-temurin:21-jre-jammy

WORKDIR /app
COPY target/*.jar app.jar
COPY .env .

ENTRYPOINT ["java", \
            "-XX:MaxRAM=400m", \
            "-XX:+UseSerialGC", \
            "-Xss256k", \
            "-jar", \
            "app.jar"]