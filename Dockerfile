# Usa uma imagem base do Java 17
FROM eclipse-temurin:17-jdk-jammy

# Diretório de trabalho dentro do container
WORKDIR /app

# Copia o arquivo JAR da sua aplicação (gerado pelo Maven/Gradle)
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

# Porta que a API vai rodar (a mesma do seu application.properties)
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
