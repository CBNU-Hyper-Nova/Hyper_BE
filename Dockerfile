# Java 17 이미지 기반
FROM openjdk:17-jdk-slim

# Gradle Wrapper와 소스 코드 복사
COPY gradlew /app/gradlew
COPY gradle /app/gradle
COPY build.gradle /app/build.gradle
COPY settings.gradle /app/settings.gradle
COPY src /app/src

# 작업 디렉토리 설정
WORKDIR /app

# Gradle Wrapper에 실행 권한 부여
RUN chmod +x gradlew

# Gradle 빌드를 실행하여 JAR 파일 생성 (테스트 생략)
RUN ./gradlew build --no-daemon -x test

# 빌드 결과물 복사
COPY build/libs/*.jar app.jar

# application.yml 파일 복사 (src/main/resources 경로에서 복사)
COPY src/main/resources/application.yml /app/application.yml

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
