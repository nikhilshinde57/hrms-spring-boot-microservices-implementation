# Sample HR Management Software (HRMS) App using Spring Boot Microservices Architecture

Goal behind starting this project

After reading so many blogs, books about microservice we think like yes it's easy what's new in microservice it's just writing small small services based on Domain-Driven Design (DDD) subdomains.
But in practice it comes up with some challenges and what are those challenges, how we can resolve them, how actual microservice development happens, what steps to take in the initial to start with microservice development, how its working in production, etc etc
All these questions excited me to start writing this project.  

About HRMS Application
The simple plane hrms application will be having following functionalities
* **HR able to create/update/delete the organization**
* **HR able to create/update/delete an employee under the organization**
* **HR able to allocate the leaves to an organization**
* **HR able to allocate the leaves to an employee**
* **Employee can apply/cancel leaves based on allocated leaves**
* **Based on role an employee (TL,Manger) can approve/reject the leaves of an employees those are under his/her**


This HRMS application demonstrates how to build an application using microservices architecture paradigm with Polyglot Languages (Java), frameworks like Spring Boot, & Polyglot Persistence software (Postgres).
The application includes following functional microservices & infrastructure microservices. All of these microservices are independently deployable applications and are organized around business capabilities.

Functional Microservices
* **Organization Microservice**
* **Employee Microservice**
* ~~Leave Microservice~~ [_TODO_]
* ~~Authentication Microservice~~ [_TODO_]
* ~~ Microservice~~ [_TODO_]

Infrastructure Microservices
* **Global Configuration Microservice**
* **Service Registration & Discovery Microservice**
* ~~API Gateway~~ [_TODO_]

Prerequisites to run the application
* **_JDK 8_** - Install JDK 1.8 version from, http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
* **_Maven_** - Download latest version of Maven from https://maven.apache.org/download.cgi
* **_PostgreSQL_** - Download and install PostgreSQL from, https://www.postgresql.org/download/
* **_IntelliJ IDEA Community Edition [Optional]_** - Install IntelliJ IDEA Community Edition from, https://www.jetbrains.com/idea/#chooseYourEdition

Installation
#### Clone Repository
Clone repository source code by executing following instruction to any folder on your machine,
```
https://github.com/nikhilshinde57/hrms-spring-boot-microservices-sample.git
cd hrms-spring-boot-microservices-sample
```

Building Application
#### Creating Database
Setup postgres database on local machine and apply script src/main/resources/sql-scripts/db-setup-script.sql
#### Building and Run the Microservices
[Maven](http://maven.apache.org/guides/getting-started/) has been used as a build tool to build Spring Boot based Microservices applications.
Currently we need to run the single single microservice independently, so issue following command on your terminal/console window in separate window for each service,
##### Service Registration & Discovery Microservice
```
cd eureka-service
mvn clean install
mvn spring-boot:run
```
##### Global Configuration Microservice
```
cd config-server
mvn clean install
mvn spring-boot:run
```
##### Organization Microservice
```
cd organization-service
mvn clean install
mvn spring-boot:run
```
##### Employee Microservice
```
cd employee-service
mvn clean install
mvn spring-boot:run
```

##### Check all services are up and running in service registration service 
Service Registration Microservice: http://localhost:8761/. Here you can see all services are up or not and they are registered or not.
##### For Functional Microservices you can check the all REST API's details on swagger ui.
Swagger ui for Organization Microservice: http://localhost:8030/swagger-ui.html<br />
Swagger ui for Employee Microservice: http://localhost:8040/swagger-ui.html<br />

