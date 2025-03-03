# Etapa 1: Construcción de la aplicación con Maven
FROM maven:3.9.8-eclipse-temurin-21 AS build

# Definir el directorio de trabajo en /app
WORKDIR /app

# Copiar el archivo de configuración de Maven (pom.xml) y descargar dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar el código fuente de la aplicación
COPY src ./src

# Compilar la aplicación con el perfil de producción
RUN mvn clean package -Pproduccion -DskipTests

# Etapa 2: Imagen final con solo la aplicación (ligera)
FROM eclipse-temurin:21-jdk-alpine

# Definir el directorio de trabajo en /app
WORKDIR /app

# Copiar solo el JAR desde la fase anterior
COPY --from=build /app/target/*.jar app.jar
COPY .env .

# Exponer el puerto de la aplicación (si es necesario)
EXPOSE 8080

# Definir el punto de entrada con ajustes de memoria
ENTRYPOINT ["java", \
            "-Xms512m", "-Xmx1024m", \
            "-XX:+UseG1GC", \
            "-jar", "app.jar"]
