FROM openjdk:8
MAINTAINER Nikhil Shinde <nikhilshinde57@gmail.com>
VOLUME /tmp
COPY ./wait-for.sh /
WORKDIR /
RUN chmod +x ./wait-for.sh
ARG JAR_FILE=/target/config-service-1.0-SNAPSHOT.jar
COPY ${JAR_FILE} /config-service-1.0-SNAPSHOT.jar
EXPOSE 8888
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/config-service-1.0-SNAPSHOT.jar"]
