# Sample HR Management Software (HRMS) App using Spring Boot Microservices Architecture

#### Thought behind starting this project

After reading so many blogs, books about microservices we think like yes it's easy what's new in microservices it's just writing separate services based on Domain-Driven Design (DDD) subdomains.
But in practice it comes up with some challenges and what are those challenges? How we can resolve them ? How actual microservice development works? What steps need to take in the initial phase to start with microservice development? How is it works in production? etc
All these questions excited me to start writing this project. 

#### About HRMS Application
The simple plain HRMS application will be having following functionalities
* **HR will be able to create/update/delete the organization**
* **HR will be able to create/update/delete an employee under the organization**
* **HR will be able to allocate the leaves to an organization**
* **HR will be able to allocate the leaves to an employee**
* **Employee can apply/cancel leaves based on allocated leaves**
* ~~Based on role an employee (TL,Manger) can approve/reject the leaves of an employees those are under his/her~~ [TODO]


This HRMS application demonstrates how to build an application using microservices architecture paradigm with Polyglot Languages (Java), frameworks like Spring Boot & Polyglot Persistence software (Postgres).
The application includes following functional microservices & infrastructure microservices. All of these microservices are independently deployable applications and are organized around business capabilities.

## Functional Microservices
* **Organization Microservice**
* **Employee Microservice**
* **Leave Microservice**
* ~~Authentication Microservice~~ [_TODO_]

## Infrastructure Microservices
* **Global Configuration Microservice**
* **Service Registration & Discovery Microservice**
* **API Gateway**

## Prerequisites
* **_JDK 8_** - Install JDK 1.8 version from [here](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* **_Maven_** - Download latest version of Maven from [here](https://maven.apache.org/download.cgi)
* **_PostgreSQL_** - Download and install PostgreSQL from [here](https://www.postgresql.org/download/)
* **_IntelliJ IDEA Community Edition [Optional]_** - Install IntelliJ IDEA Community Edition from [here](https://www.jetbrains.com/idea/#chooseYourEdition)
* **_Docker [Optional]_** Install Docker from [here](https://www.docker.com/products/docker-desktop)

## Installation
#### Repository
Clone repository source code by executing following instruction to any folder on your machine,
```
https://github.com/nikhilshinde57/hrms-spring-boot-microservices-implementation.git
cd hrms-spring-boot-microservices-implementation
```

## Building Application
#### Creating Database
Setup postgres database on local machine and apply script db-setup-script.sql script located at this path src/main/resources/sql-scripts/db-setup-script.sql
#### Build and Run the Microservices locally
[Maven](http://maven.apache.org/guides/getting-started/) has been used as a build tool to build Spring Boot based Microservices applications.
To run a single microservice independently, use the following command on your terminal/console window in separate window for each service.

##### Service Registration & Discovery Microservice
```
cd eureka-service
mvn clean install
mvn spring-boot:run -Dspring.profiles.active=local
```
##### Global Configuration Microservice
```
cd config-server
mvn clean install
mvn spring-boot:run -Dspring.profiles.active=local
```
##### Organization Microservice
```
cd organization-service
mvn clean install
mvn spring-boot:run -Dspring.profiles.active=local
```
##### Employee Microservice
```
cd employee-service
mvn clean install
mvn spring-boot:run -Dspring.profiles.active=local
```
##### Leave Microservice
```
cd leave-service
mvn clean install
mvn spring-boot:run -Dspring.profiles.active=local
```

##### Gateway Microservice
```
cd gateway-service
mvn clean install
mvn spring-boot:run -Dspring.profiles.active=local
```
 
#### Service Discovery Microservice: 
[Here](http://localhost:8761/) you can check all services are up or not and they are registered or not.
#### Gateway Service:
[Here](http://localhost:8762/routes) you can check all the available routes.

```
// 20200512201118
// http://localhost:8762/routes

{
  "/leave-service/**": "leave-service",
  "/employee-service/**": "employee-service",
  "/config-service/**": "config-service",
  "/organization-service/**": "organization-service"
}

Now using above Gateway Service routes we can now call the other services like 
To get all employess we use: http://localhost:8762/employee-service/api/employees
To get all organizations we use: http://localhost:8762/organization-service/api/organizations  
```

### Health Checks

- [Organization Service Health Check](http://localhost:8030/actuator/health)
- [Employee Service Health Check](http://localhost:8040/actuator/health)
- [Leave Service Health Check](http://localhost:8050/actuator/health)
- [Config Service Health Check](http://localhost:8888/actuator/health)

### Application Metrics

- [Organization Service Metrics](http://localhost:8030/actuator/metrics)
- [Employee Service Metrics](http://localhost:8040/actuator/metrics)
- [Leave Service Metrics](http://localhost:8050/actuator/metrics)
- [Config Service Metrics](http://localhost:8888/actuator/metrics)

### Swagger UI For Functional Microservices
- [Organization Microservice](http://localhost:8030/swagger-ui.html)
- [Employee Microservice](http://localhost:8040/swagger-ui.html)
- [Leave Microservice](http://localhost:8050/swagger-ui.html)

### Docker Environment Notes:
[Maven](http://maven.apache.org/guides/getting-started/) has been used as a build tool to build Spring Boot based Microservices applications.
```
mvn clean install
```
This command might take a while for first time as it needs to download serveral dependency libraries from Maven repository. This command will build & package all microservice applications.

### Building Docker Containers & Run Containers
To run the docker containers make sure that you have installed and running docker on your machine by running below command on your machine's terminal window.
```
> docker --version
Docker version 19.03.8, build afacb8b
```
Upon successful building of microservices, you can now build Docker images and run containers Docker host on your machine. Note: If on your local machine you are running Postgres on port 5432 then stop that service before running below command. 

Issue following command under the docker-compose directory, to run Docker containers,
```
docker-compose up
```
Above command starts all Microservices Docker containers as specified in 'docker-compose.yml' file. It will take some time to create and pull all the images and to run the containers. 

##### Check all services are up and registered to discovery service 
Service Discovery Microservice: [Here](http://localhost:8761/) you can check all services are up or not and they are registered or not.<br /><br />
The rest of all the services are exposed on the same port as mentioned earlier.