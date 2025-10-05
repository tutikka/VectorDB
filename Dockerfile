FROM openjdk:17-jdk-slim
RUN groupadd --gid 1000 vectordb && useradd --uid 1000 --gid 1000 vectordb --home-dir /home/vectordb
USER vectordb
WORKDIR /home/vectordb
COPY target/*.jar /home/vectordb/vectordb.jar
COPY vectordb.properties /home/vectordb/vectordb.properties
ENTRYPOINT ["java", "-jar", "vectordb.jar"]