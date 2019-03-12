# Kotlin Rest Sample

`book-entity-service` is a sample REST microservice application.

## Implementation

Following tech spec is used for the TDD based implementation.

- *Kotlin* 
- *Spring Boot* version 2.1.3-RELEASE 
- *maven* version 3.6.0 
- *hibernate*
- *JUnit* 
- *h2*

The project is organized as a *maven* project and in order to compile, test and create a package *maven* is used.

### Building the application

You could use maven to test and build the jar file.

* In the root directory of the project run the following commands

```bash
# Compile
mvn -B clean compile

#Test
mvn -B clean test


#Create the package
mvn -B clean package

```

## Using the API

### Running the application

* Start the application with the following command:

```bash

#Under the root folder of the project

java -jar target/book-entity-service-1.0-SNAPSHOT.jar

```

* Call the API endpoint at 

`http://localhost:8080/book-entity-service/books/{isbn}.` 

using **curl**, **postman** or **web browser**.  

* You could update the *port* and *contextPath* in the `application.yml` file.

### Request

The endpoint of the application as given in the following table.

|End Point      | Operation    |Port  |
|---------------|--------------|------|
|/books         |GET           | 8080 |
|/books/{isbn}  |GET           | 8080 |
|/books         |POST          | 8080 |
|/books/{isbn}  |PUT           | 8080 |
|/books/{isbn}  |DELETE        | 8080 |

- contextPath is given as `book-entity-service` in the `application.yml` configuration file.
- isbn: The ISBN number of a book

### Response 

|Http Status | Description         | Body                     | more Information   |
|------------|---------------------|--------------------------|--------------------|
|200         | OK                  | {book}                   | Book information   |
|400         | BadRequest          | {error}                  | Invalid request    |
|404         | NotFound            | {error}                  | BookNotFound       |
|404         | UnprocessableEntity | {error}                  | BookAlreadyExist   |
|500         | InternalServerError | {error}                  | TechnicalFailure   |

## Database
 
 |Column Name      | Type                | Not Null |
 |-----------------|---------------------|----------|
 |ISBN             | VARCHAR(64)         | Y        |
 |TITLE            | VARCHAR(256)        | Y        |
 |AUTHOR           | VARCHAR(64)         | Y        |
 |PUBLISHER        | VARCHAR(64)         | Y        |
 |EDITION          | INT                 | N        |
 |CREATION_DATE    | TIMESTAMP           | Y        |
 |MODIFIED_DATE    | TIMESTAMP           | Y        |
