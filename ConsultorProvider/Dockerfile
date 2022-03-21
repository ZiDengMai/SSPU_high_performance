FROM adoptopenjdk/openjdk8:latest
VOLUME /tmp
ADD target/ConsultorProvider-1.0-SNAPSHOT-exec.jar /ConsultorProvider.jar
ENTRYPOINT ["java","-jar","/ConsultorProvider.jar"]
