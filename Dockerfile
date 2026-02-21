FROM eclipse-temurin:21-jre
RUN groupadd -r vectordb && useradd -r -g vectordb -d /home/vectordb -m vectordb
WORKDIR /home/vectordb
COPY --chown=vectordb:vectordb target/vectordb-*.jar vectordb.jar
COPY --chown=vectordb:vectordb vectordb.properties .
USER vectordb
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75", "-jar", "vectordb.jar"]