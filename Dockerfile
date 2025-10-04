FROM openjdk:17-jdk-alpine
RUN addgroup -S vectordb && adduser -S vectordb -G vectordb
USER vectordb:vectordb
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]