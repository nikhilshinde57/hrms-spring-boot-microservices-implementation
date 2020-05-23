FROM openjdk:8
MAINTAINER Nikhil Shinde <nikhilshinde57@gmail.com>
VOLUME /tmp
RUN apt-get -q update && apt-get -qy install netcat
COPY ./wait-for.sh /
WORKDIR /
RUN chmod +x ./wait-for.sh
ARG JAR_FILE=/target/gateway-service-1.0-SNAPSHOT.jar
COPY ${JAR_FILE} /gateway-service-1.0-SNAPSHOT.jar
EXPOSE 8762
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/gateway-service-1.0-SNAPSHOT.jar"]