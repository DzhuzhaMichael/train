# train
This is simple train-builder that gives opportunity to build the train according to the following model:


## technologies
* Apache Maven
* Checkstyle plugin
* Hibernate
* Java 11
* JUnit 5
* Mockito
* Spring Data
* Project Lombok

## Maven clean package
The application supports source code checks regarding Google coding conventions from 
Google Java Style that can be found at https://google.github.io/styleguide/javaguide.html. 
Use command mvn clean package in terminal to activate the checking.

## Working with DB
#### REMARK: to run the tests you don't need to provide a connection with real DB. For this purpose, in-memory DB is used.
For running the program with real DB you would need to install MySQL and configure 
`src/main/resources/db.properties` using your URL, USERNAME, PASSWORD, JDBC_DRIVER.

