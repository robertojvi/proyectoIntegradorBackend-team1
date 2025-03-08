# Usa la imagen oficial de Eclipse Temurin para JRE 21 basada en Alpine (ligera)
FROM eclipse-temurin:21-jre-alpine

# Establece el directorio de trabajo
WORKDIR /app

# Copia el jar compilado al contenedor; asegúrate de que el nombre coincide
COPY target/backendProyectoIntegrador-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto en el que corre la aplicación (ajusta si usas otro puerto)
EXPOSE 8080

# Comando para iniciar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
