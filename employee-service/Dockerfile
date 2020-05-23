FROM openjdk:8
MAINTAINER Nikhil Shinde <nikhilshinde57@gmail.com>
RUN apt-get -q update && apt-get -qy install netcat
VOLUME /tmp
COPY ./ ./wait-for.sh /
WORKDIR /
RUN chmod +x ./wait-for.sh
ARG JAR_FILE=/target/employee-service-1.0-SNAPSHOT.jar
COPY ${JAR_FILE} /employee-service-1.0-SNAPSHOT.jar
EXPOSE 8040
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/employee-service-1.0-SNAPSHOT.jar"]