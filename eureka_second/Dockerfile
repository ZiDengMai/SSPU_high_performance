FROM adoptopenjdk/openjdk8:latest
VOLUME /tmp
ADD target/eureka_second-1.0-SNAPSHOT-exec.jar /Eureka.jar
ENTRYPOINT ["java","-jar","/Eureka.jar"]
