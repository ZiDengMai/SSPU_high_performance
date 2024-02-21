FROM adoptopenjdk/openjdk8:latest
VOLUME /tmp
ADD target/ConsultorConsumer-1.0-SNAPSHOT.jar /ConsultorConsumer.jar
ENTRYPOINT ["java","-jar","/ConsultorConsumer.jar"]
