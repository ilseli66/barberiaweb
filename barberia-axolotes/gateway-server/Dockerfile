# Paso 1: Usar una imagen ligera de Java 25 (Alpine Linux es muy pequeña y rápida)
FROM eclipse-temurin:25-jdk-alpine

# Paso 2: Crear un directorio de trabajo dentro del contenedor
WORKDIR /app

# Paso 3: Copiar el archivo .jar generado por Maven/Gradle desde tu carpeta 'target'
# Usamos un comodín (*) para que no importe si la versión del jar cambia
COPY target/*.jar app.jar

# Paso 4: Informar qué puerto usa el contenedor (informativo)
# Cámbialo según el puerto de cada microservicio (8761, 8080, 8081, etc.)
EXPOSE 8080

# Paso 5: Comando para ejecutar la aplicación al encender el contenedor
# Optimizamos la memoria para la JVM en entornos de nube
ENTRYPOINT ["java", "-Xmx512m", "-Xms256m", "-jar", "app.jar"]