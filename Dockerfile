# Imagen base con OpenJDK 23
FROM eclipse-temurin:23-jdk

# Crea un directorio de trabajo
WORKDIR /app

# Copia el archivo JAR al contenedor
COPY target/*.jar app.jar

# Expón el puerto estándar (Render usa el puerto 8080 por defecto)
EXPOSE 8080

# Comando para ejecutar tu aplicación Spring Boot
CMD ["java", "-jar", "app.jar"]