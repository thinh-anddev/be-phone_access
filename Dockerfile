# Dùng image OpenJDK nhẹ
FROM openjdk:17-jdk-slim

# Tạo thư mục làm việc trong container
WORKDIR /app

# Copy file JAR vào container (bạn build từ Maven)
COPY target/Api-springboot-0.0.1-SNAPSHOT.jar app.jar

# Chạy app
ENTRYPOINT ["java", "-jar", "app.jar"]
