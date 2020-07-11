# Sample HR Management Software (HRMS) App using Spring Boot Microservices Architecture

#### Thought behind starting this project

After reading so many blogs, books about microservices we think like yes it's easy what's new in microservices it's just writing separate services based on Domain-Driven Design (DDD) subdomains.
But in practice it comes up with some challenges and what are those challenges? How we can resolve them ? How actual microservice development works? What steps need to take in the initial phase to start with microservice development? How is it works in production? etc
All these questions excited me to start writing this project. 

## Goal to Accomplish
#### Understand and Implement
* **Service Registration & Discovery Microservice using spring cloud eureka-server**
* **Central Config Service from where we can fetch/update configured value at runtime**
* **Individual Microservices and how they register itself at Discovery/Registration Microservice and fetch the configured values from config service**
* **How to call one microservice form another microservice using FeignClient/RestTemplate**
* **Hystrix Circuit Breaker**
* **API Gateway**
* **Microservice Health checks API using Spring Boot Actuator** Application metrics
* **Microservice Application Metrics**
* **Authentication Server and Resource Server with OAuth2**
* **How to Secure microservices**
* **How to call microservices with oauth token**
* **How to Dockerize microservices**
* **How Dockerized individual microservice communicate between each other**

## About HRMS Application
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
* **Authentication Microservice**

## Infrastructure Microservices
* **Global Configuration Microservice**
* **Service Registration & Discovery Microservice**
* **API Gateway Microservice**

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
##### Authentication Microservice
```
cd oauth2-authentication-service
mvn clean install
mvn spring-boot:run -Dspring-boot.run.profiles=local
```
##### Organization Microservice
```
cd organization-service
mvn clean install
mvn spring-boot:run -Dspring-boot.run.profiles=local
```
##### Employee Microservice
```
cd employee-service
mvn clean install
mvn spring-boot:run -Dspring-boot.run.profiles=local
```
##### Leave Microservice
```
cd leave-service
mvn clean install
mvn spring-boot:run -Dspring-boot.run.profiles=local
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

## Health Checks

- [Organization Service Health Check](http://localhost:8030/actuator/health)
- [Employee Service Health Check](http://localhost:8040/actuator/health)
- [Leave Service Health Check](http://localhost:8050/actuator/health)
- [Authentication Service Health Check](http://localhost:8060/actuator/health)
- [Config Service Health Check](http://localhost:8888/actuator/health)

## Application Metrics

- [Organization Service Metrics](http://localhost:8030/actuator/metrics)
- [Employee Service Metrics](http://localhost:8040/actuator/metrics)
- [Leave Service Metrics](http://localhost:8050/actuator/metrics)
- [Authentication Service Metrics](http://localhost:8060/actuator/metrics)
- [Config Service Metrics](http://localhost:8888/actuator/metrics)

## Swagger UI For Functional Microservices
- [Organization Microservice](http://localhost:8030/swagger-ui.html)
- [Employee Microservice](http://localhost:8040/swagger-ui.html)
- [Leave Microservice](http://localhost:8050/swagger-ui.html)

## Docker Environment Notes:
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

## Token Retrieval

When the all services are up you can signup up into the system using Authentication service.
Here we have used the Oauth2 in that we implemented our own Authentication Server.
The all our microservices are treated as a resources and we need to authenticate and authorize a user when he wants to access our resource.
At the time of starting of an Authentication microservice we have added/registered the client to our authentication server.
There are three main roles using that use can SingUp. Those roles are: [ADMIN,TL,USER]
```
USER CAN: 
    Resource: Organizations
        Operations: GetHisOrganizationInformation
    Resource: Employee
        Operations: Search/UpdateHisOwnInformation
    Resource: Employee Leave allocation
        Operations: NONE
    Resource: Organization Leave allocation
        Operations: NONE
    Resource: Employee Leave application
        Operations: Apply/UpdateHisOwnLeaveApplication
TL CAN: 
    Resource: Organizations
        Operations: Same as USER can do
    Resource: Employee
        Operations: Same as USER can do
    Resource: Employee Leave allocation
        Operations:NONE
    Resource: Organization Leave allocation
        Operations: NONE
    Resource: Employee Leave application
        Operations:  Same as USER can do + GetTeamMembersLeaveApplications
ADMIN CAN: 
    Resource: Organizations
        Operations: Same as TL can do + Create/Update/Delete/GetAll
    Resource: Employee
        Operations:  Same as TL can do + Create/Update/Delete/GetAll
    Resource: Employee Leave allocation
        Operations: Allocate/Update/Delete/GetAll
    Resource: Organization Leave allocation
        Operations: Allocate/Update/Delete/GetAll
    Resource: Employee Leave application
        Operations: Same as TL can do + Apply/Update/Get/GetAll

```
Here client means the one who want to access/call our resources/microservice like it can be any webui application, mobile app or even postman from where we are accessing/calling the resource.
The registered client details you can find it in client_details table of authentication service DB. Client details:
```
client_id:hrms
client_secret:yDx01x2YfQ
authorized_grant_types:password
scope:webclient
```
The user can SignUp by making below request.
#### SingUp Request
```
POST /oauth/signup HTTP/1.1
Host: localhost:8060
Content-Type: application/json
Cache-Control: no-cache
Postman-Token: 75389844-7db3-b93f-be1a-fb85883cec45

{
	"username":"nikhil",
	"email":"niikhilshinde57@gmail.com",
	"password":"password1",
	"role":["admin"]
}
```
#### SignUp Response:
```
{
    "message": "User registered successfully!"
}
```
#### Get OAuth Token Request:
To get the token user need to send the client details like client_id and secret in Authorization and in request body he need to send grant_type,scope, username, password.
As mentioned earlier the registered client is having values like client_id:hrms and client_secret:yDx01x2YfQ.
The all postman request call file and screenshots of token retrieval process you can find it in /postman_request_file_and_screenshots folder.     
```
POST /oauth/token HTTP/1.1
Host: localhost:8060
Authorization: Basic {base64_encode(hrms:yDx01x2YfQ)}
Cache-Control: no-cache
Postman-Token: 0511ef73-1012-2709-3b96-753c5983c585
Content-Type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW

------WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="grant_type"

password
------WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="scope"

webclient
------WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="username"

nikhil
------WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="password"

password1
------WebKitFormBoundary7MA4YWxkTrZu0gW--
```
#### Get OAuth Token Response
```
{
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1OTMzNjc0MjAsInVzZXJfbmFtZSI6Im5pa2hpbCIsImF1dGhvcml0aWVzIjpbIlJPTEVfQURNSU4iXSwianRpIjoiOTA0Yjg5YTItYTRjYy00NTg5LTk0ODUtNmFlZmYzZmFjOTM2IiwiY2xpZW50X2lkIjoiaHJtcyIsInNjb3BlIjpbIndlYmNsaWVudCJdfQ.xjzOFQRik4R-PoKxwfw5xrHu-dEouGOqNm9Zcjpy5PA",
    "token_type": "bearer",
    "expires_in": 899,
    "scope": "webclient",
    "jti": "904b89a2-a4cc-4589-9485-6aeff3fac936"
}
```
#### Validate Token Request
```
GET /oauth/user HTTP/1.1
Host: localhost:8060
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1OTMzNjc0MjAsInVzZXJfbmFtZSI6Im5pa2hpbCIsImF1dGhvcml0aWVzIjpbIlJPTEVfQURNSU4iXSwianRpIjoiOTA0Yjg5YTItYTRjYy00NTg5LTk0ODUtNmFlZmYzZmFjOTM2IiwiY2xpZW50X2lkIjoiaHJtcyIsInNjb3BlIjpbIndlYmNsaWVudCJdfQ.xjzOFQRik4R-PoKxwfw5xrHu-dEouGOqNm9Zcjpy5PA
Cache-Control: no-cache
Postman-Token: 560b822e-5b5f-a2e1-9f14-d5c6e79b0738
```
#### Validate Token Response 
```
{
    "clientId": "hrms",
    "scope": [
        "webclient"
    ],
    "user": "nikhil",
    "authorities": [
        "ROLE_ADMIN"
    ]
}
```
Now using above received token we can access the resources(microservices) by sending that token in header. 
#### Access Organization Service Request
```
GET /employee-service/api/employees/ HTTP/1.1
Host: localhost:8762
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1OTMzNjc0MjAsInVzZXJfbmFtZSI6Im5pa2hpbCIsImF1dGhvcml0aWVzIjpbIlJPTEVfQURNSU4iXSwianRpIjoiOTA0Yjg5YTItYTRjYy00NTg5LTk0ODUtNmFlZmYzZmFjOTM2IiwiY2xpZW50X2lkIjoiaHJtcyIsInNjb3BlIjpbIndlYmNsaWVudCJdfQ.xjzOFQRik4R-PoKxwfw5xrHu-dEouGOqNm9Zcjpy5PA
Cache-Control: no-cache
Postman-Token: 695cc1e3-3357-e607-df66-7a98c8f627b2
```
#### Access Employee Service Request
```
GET /organization-service/api/organizations/ HTTP/1.1
Host: localhost:8762
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1OTMzNjc0MjAsInVzZXJfbmFtZSI6Im5pa2hpbCIsImF1dGhvcml0aWVzIjpbIlJPTEVfQURNSU4iXSwianRpIjoiOTA0Yjg5YTItYTRjYy00NTg5LTk0ODUtNmFlZmYzZmFjOTM2IiwiY2xpZW50X2lkIjoiaHJtcyIsInNjb3BlIjpbIndlYmNsaWVudCJdfQ.xjzOFQRik4R-PoKxwfw5xrHu-dEouGOqNm9Zcjpy5PA
Cache-Control: no-cache
Postman-Token: d320293c-7ce5-f86d-16c6-f2dce7a5f1f1
```
#### Access Leave Service Request
```
GET /leave-service/api/leave-allocation/organizations HTTP/1.1
Host: localhost:8762
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1OTMzNjc0MjAsInVzZXJfbmFtZSI6Im5pa2hpbCIsImF1dGhvcml0aWVzIjpbIlJPTEVfQURNSU4iXSwianRpIjoiOTA0Yjg5YTItYTRjYy00NTg5LTk0ODUtNmFlZmYzZmFjOTM2IiwiY2xpZW50X2lkIjoiaHJtcyIsInNjb3BlIjpbIndlYmNsaWVudCJdfQ.xjzOFQRik4R-PoKxwfw5xrHu-dEouGOqNm9Zcjpy5PA
Cache-Control: no-cache
Postman-Token: 4b1d748a-b00e-7932-ceb7-c7fcc1b93e06
```
Note: Above API endpoints are the API Gateway endpoint's you can make a call to individual service like shown below:
```
GET /api/organizations/1 HTTP/1.1
Host: localhost:8030
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1OTMzNjc0MjAsInVzZXJfbmFtZSI6Im5pa2hpbCIsImF1dGhvcml0aWVzIjpbIlJPTEVfQURNSU4iXSwianRpIjoiOTA0Yjg5YTItYTRjYy00NTg5LTk0ODUtNmFlZmYzZmFjOTM2IiwiY2xpZW50X2lkIjoiaHJtcyIsInNjb3BlIjpbIndlYmNsaWVudCJdfQ.xjzOFQRik4R-PoKxwfw5xrHu-dEouGOqNm9Zcjpy5PA
Cache-Control: no-cache
Postman-Token: ac87917f-ab3f-8ae4-4011-7f3a89f56a80
```