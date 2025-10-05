FROM openjdk:17-jdk-slim
RUN groupadd --gid 1000 vectordb && useradd --uid 1000 --gid 1000 vectordb
USER vectordb
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY vectordb.properties .
ENTRYPOINT ["java", "-jar", "/app.jar"]