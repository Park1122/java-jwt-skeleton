# Build Stage
FROM bellsoft/liberica-openjdk-alpine:17 AS builder

WORKDIR /app

COPY . .

# 테스트 제외
RUN ./gradlew clean build -x test


# Run Stage
FROM bellsoft/liberica-openjdk-alpine:17

WORKDIR /app

ENV TZ=Asia/Seoul

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]