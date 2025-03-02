# Usa una imagen más ligera con JRE (no JDK)
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Copia solo el JAR, no el código fuente
COPY target/*.jar app.jar

# Limita la memoria de la JVM
ENTRYPOINT ["java", "-XX:MaxRAM=512m", "-XX:+UseSerialGC", "-jar", "app.jar"]