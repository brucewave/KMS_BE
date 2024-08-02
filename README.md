# Kindergraten Management System

## Description

Backend for Kindergarten Management System

## Get Started
### Prerequisites
* Java (Version 20) (https://www.oracle.com/java/technologies/javase/jdk20-archive-downloads.html)
* Maven (https://maven.apache.org/download.cgi)
### Configuration
* Clone the repository:
```
git clone https://github.com/MrThinkJ/KindergartenManagementSystem.git
```
* Configuration properties can be found in the application.properties file located in the src/main/resources directory.
* Change `xxx` value with you value
### Running the application
1. Navigate to the project directory:
```
cd KinderGartenManagementSystem
```
2. Run the application:
```
mvn spring-boot:run
```
This will start the application on port [8080] by default. You can access the application at http://localhost:[8080].
### Building the application
You can build a JAR file for your application using the following command:
```
mvn package
```
The JAR file will be located in the target directory.
### Testing
Unit tests are located in the src/test/java directory. You can run the tests using the following command:
```
mvn test
```
### API Document
http://localhost:8080/swagger-ui/index.html
