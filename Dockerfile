FROM eclipse-temurin:21-jre-jammy

WORKDIR /app
COPY target/*.jar app.jar

# Usar formato de array correcto para los parámetros JVM
ENTRYPOINT ["java", \
            "-XX:MaxRAM=400m", \
            "-XX:+UseSerialGC", \
            "-Xss256k", \
            "-jar", \
            "app.jar"]