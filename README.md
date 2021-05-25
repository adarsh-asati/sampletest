<h1>Technical Test</h1>

## Description
This Spring Boot Command Line project takes one parameter, path to a text file which contains log and application log entries in valid JSON format. 
When the process is finished logs are entered into Entry table in hsqldb

## Requirements
Java 8

## Running tests, build and running jar
```
cd PROJECT_DIR
./mvn clean install
java -jar target/logcollector-0.0.1-SNAPSHOT.jar <path/to/input/file>
```
