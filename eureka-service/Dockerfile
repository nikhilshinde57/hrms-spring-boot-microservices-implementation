FROM openjdk:8-jdk-alpine
MAINTAINER Nikhil Shinde <nikhilshinde57@gmail.com>
VOLUME /tmp
ARG JAR_FILE=/target/*.jar
COPY ${JAR_FILE} /eureka-service-1.0-SNAPSHOT.jar
EXPOSE 8761
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/eureka-service-1.0-SNAPSHOT.jar"]