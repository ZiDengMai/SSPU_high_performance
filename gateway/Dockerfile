FROM adoptopenjdk/openjdk8:latest
VOLUME /tmp
ADD target/gateway-1.0-SNAPSHOT-exec.jar /Gateway.jar
ENTRYPOINT ["java","-jar","/Gateway.jar"]
