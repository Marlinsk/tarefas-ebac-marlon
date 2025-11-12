# Multi-stage build para otimizar o tamanho da imagem
FROM eclipse-temurin:21-jdk-jammy AS builder

WORKDIR /app

# Copiar arquivos do Gradle
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Copiar código fonte
COPY src src

# Build da aplicação (sem executar testes para acelerar)
RUN ./gradlew bootJar -x test

# Estágio final - imagem mínima
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Criar usuário não-root para segurança
RUN groupadd -r spring && useradd -r -g spring spring
USER spring:spring

# Copiar JAR do estágio de build
COPY --from=builder /app/build/libs/*.jar app.jar

# Expor porta do gateway
EXPOSE 9000

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
    CMD curl -f http://localhost:9000/actuator/health || exit 1

# Executar aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
