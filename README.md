# CS-489-APPSD

Maven-based Java CLI solutions and submission assets for:

- Lab 1: Product Management app
- Lab 2A: Employee Pension Plans app
- Lab 2B: Patient Appointment Management System (PAMS)
- Lab 3: Software Requirements Discovery and Domain Modeling for Advantis Dental Surgeries (ADS)
- Lab 4: Software Solution Architecture for Advantis Dental Surgeries (ADS)
- Lab 6: Data Persistence for Advantis Dental Surgeries (ADS)
- Lab 7: RESTful Web API for Advantis Dental Surgeries (ADS)
- Lab 7b: GraphQL Web API for Advantis Dental Surgeries (ADS)

Java version: 21+

## Build

```bash
mvn clean package
```

## Run Lab 2A

```bash
java -cp target/classes edu.miu.cs.cs489appsd.lab2a.employeepensionapp.EmployeePensionApp
```

Optional: pass a reference date in ISO format to control which quarter is treated as the "next quarter".

```bash
java -cp target/classes edu.miu.cs.cs489appsd.lab2a.employeepensionapp.EmployeePensionApp 2025-10-01
```

## Run Lab 2B

```bash
java -cp target/classes edu.miu.cs.cs489appsd.lab2b.pamsapp.PAMSApp
```

The Lab 2B app writes its JSON output to:

```text
outputs/lab2b/patients-by-age.json
```

## Run Lab 3

```bash
java -cp target/classes edu.miu.cs.cs489appsd.lab3.adsapp.ADSApp
```

The Lab 3 submission assets are committed under:

```text
outputs/lab3/ads-functional-requirements.md
outputs/lab3/ads-domain-model.svg
outputs/lab3/ads-domain-model.png
```

## Lab 4 Deliverables

The Lab 4 architecture deliverables are committed under:

```text
outputs/lab4/ads-architecture-tech-stack.md
outputs/lab4/ads-solution-architecture.svg
outputs/lab4/ads-solution-architecture.png
```

## Lab 6 Overview

Lab 6 implements enterprise data persistence for the ADS dental clinic using Spring Boot CLI and Spring Data JPA.

The solution includes:

- JPA entities and table mappings for `patients`, `dentists`, `surgeries`, `addresses`, `appointments`, `users`, and `roles`
- Primary key and foreign key relationships matching the provided domain model
- Java-based sample data initialization based on the supplied raw appointment data
- Basic CRUD demonstrations executed automatically at application startup

Main Lab 6 source files:

```text
src/main/java/edu/miu/cs/cs489appsd/lab6/adsapp/
src/main/java/edu/miu/cs/cs489appsd/lab6/adsapp/model/
src/main/java/edu/miu/cs/cs489appsd/lab6/adsapp/repository/
src/main/java/edu/miu/cs/cs489appsd/lab6/adsapp/service/
src/main/resources/application.properties
```

The Lab 6 persistence layer is reused by Lab 7 and Lab 7b. The default Spring Boot startup path now launches the Lab 7b GraphQL Web API.

## Test Lab 6

Run the automated test that verifies the Spring Boot application context loads correctly:

```bash
mvn test
```

The packaged JAR now starts Lab 7b by default. The Lab 6 persistence code remains in the `lab6/adsapp` package and is reused by both the Lab 7 REST API and the Lab 7b GraphQL API.

## Verify Lab 6 Results

When the application runs successfully, the console output should show:

- A seeded reference-data summary with users, roles, patients, dentists, surgeries, and appointments
- The appointment table populated from the provided sample raw data
- A `CREATE` section adding a temporary patient and appointment
- A `READ` section fetching patient `P105` and listing appointments for surgery `S15`
- An `UPDATE` section modifying patient `P105`
- A `DELETE` section removing the temporary records and ending with `Final appointment count -> 6`

Expected seeded appointment rows:

```text
Tony Smith         P100   Gillian White   12-Sep-13   10.00   S15
Helen Pearson      P108   Ian MacKay      12-Sep-13   10.00   S10
Tony Smith         P105   Jill Bell       12-Sep-13   12.00   S15
Helen Pearson      P108   Ian MacKay      14-Sep-13   14.00   S10
Robin Plevin       P105   Jill Bell       14-Sep-13   16.30   S15
Robin Plevin       P110   John Walker     15-Sep-13   18.00   S13
```

## Database Configuration

The runtime application uses a real local MySQL server. The automated test profile uses in-memory H2 so the integration tests can run without changing local MySQL data.

Default runtime settings in [src/main/resources/application.properties](/Users/hainingsong/IdeaProjects/CS-489-APPSD/src/main/resources/application.properties):

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/cs489_apsd_lab6_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Chicago
spring.datasource.username=root
spring.datasource.password=123456
```

You can override these defaults without editing the file by setting environment variables before running the app:

```bash
export LAB6_DB_URL="jdbc:mysql://localhost:3306/cs489_apsd_lab6_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Chicago"
export LAB6_DB_USERNAME="your_mysql_username"
export LAB6_DB_PASSWORD="your_mysql_password"
```

Then run:

```bash
java -jar target/cs489-appsd.jar
```

If your local MySQL account does not use the default `root / 123456`, set the environment variables first.
If MySQL is not running on `localhost:3306`, start the server first or replace `LAB6_DB_URL` with the correct host, port, and database settings.
On this machine, the local MySQL `root` account is configured with password `123456`, so the project defaults now work as-is.

Start the local MySQL service if needed:

```bash
brew services start mysql
```

You can verify the local MySQL login with:

```bash
mysql -u root -p123456
```

The MySQL runtime configuration is stored in:

```text
src/main/resources/application.properties
```

The automated test-profile configuration is stored in:

```text
src/test/resources/application-test.properties
```

The sample data is cleared and reseeded on each application run so the API output stays consistent while using a real MySQL database.

## Lab 7 Overview

Lab 7 implements a RESTful Web API for the ADS dental clinic using Spring Boot Web, Spring Data JPA, validation, and exception handling.

Main Lab 7 source files:

```text
src/main/java/edu/miu/cs/cs489appsd/lab7/adswebapi/
src/test/java/edu/miu/cs/cs489appsd/lab7/adswebapi/
```

Key Lab 7 components:

- Spring Boot startup and data initialization for the Web API
- REST controllers for patient and address resources
- Request and response DTOs for JSON payloads
- Global exception handling for invalid IDs, validation errors, not-found cases, and data-integrity violations
- Integration tests covering the required endpoints

## Run Lab 7

Build the project:

```bash
mvn clean package
```

Run the Lab 7 REST Web API with Maven:

```bash
mvn -Dspring-boot.run.main-class=edu.miu.cs.cs489appsd.lab7.adswebapi.Lab7RestApiApplication spring-boot:run
```

Before starting the app, make sure a local MySQL server is running and accepting connections on port `3306`.

After startup, the API is available at:

```text
http://localhost:8080/adsweb/api/v1
```

Required Lab 7 endpoints:

- `GET /adsweb/api/v1/patients`
- `GET /adsweb/api/v1/patients/{patientId}`
- `POST /adsweb/api/v1/patients`
- `PUT /adsweb/api/v1/patients/{patientId}`
- `DELETE /adsweb/api/v1/patient/{patientId}`
- `GET /adsweb/api/v1/patient/search/{searchString}`
- `GET /adsweb/api/v1/addresses`

## Test Lab 7

Run all automated tests, including the Lab 7 integration tests:

```bash
mvn test
```

The Lab 7 integration tests are located in:

```text
src/test/java/edu/miu/cs/cs489appsd/lab7/adswebapi/Lab7RestApiIntegrationTests.java
```

## Lab 7 Submission Assets

The generated Lab 7 API output files and screenshot-ready previews are committed under:

```text
screenshots/Lab7/
```

That folder includes:

- JSON output for `GET`, `POST`, `PUT`, `SEARCH`, and `GET /addresses`
- Text output for the `DELETE` response status
- PNG previews for each captured output file

## Lab 7b Overview

Lab 7b implements GraphQL Web API endpoints for the ADS dental clinic using Spring for GraphQL, Spring Web MVC, Spring Data JPA, and the Lab 6 and Lab 7 shared backend services.

Main Lab 7b source files:

```text
src/main/java/edu/miu/cs/cs489appsd/lab7b/adsgraphqlapi/
src/main/resources/graphql/ads_lab7b_schema.graphqls
src/test/java/edu/miu/cs/cs489appsd/lab7b/adsgraphqlapi/
```

Key Lab 7b components:

- A dedicated Spring Boot startup class for the GraphQL app
- GraphQL query and mutation mappings for patient and address operations
- GraphQL schema definitions under `src/main/resources/graphql/`
- Reuse of the Lab 6 persistence layer and Lab 7 DTO and service layer mappings
- Integration tests that validate the required GraphQL operations

## Run Lab 7b

Run the Lab 7b GraphQL Web API with Maven:

```bash
mvn spring-boot:run
```

Or run the packaged JAR:

```bash
java -jar target/cs489-appsd.jar
```

After startup, use:

```text
http://localhost:8080/graphiql
```

The GraphQL endpoint is available at:

```text
http://localhost:8080/graphql
```

Implemented Lab 7b operations:

- `query allPatients`
- `query patientById(patientId: ID!)`
- `query searchPatients(searchString: String!)`
- `query allAddresses`
- `mutation addNewPatient(newPatient: NewPatientInput!)`
- `mutation updatePatient(patientId: ID!, editedPatient: NewPatientInput!)`
- `mutation deletePatient(patientId: ID!)`

## Test Lab 7b

Run all automated tests, including the Lab 7b GraphQL integration tests:

```bash
mvn test
```

The Lab 7b integration tests are located in:

```text
src/test/java/edu/miu/cs/cs489appsd/lab7b/adsgraphqlapi/Lab7bGraphqlWebApiIntegrationTests.java
```

## Lab 7b Submission Assets

The GraphiQL screenshot checklist and final screenshots belong under:

```text
screenshots/Lab7b/
```

That folder includes:

- `screenshot-checklist.md` with the exact query and mutation list
- The final GraphiQL PNG screenshots for submission

## CI/CD

GitHub Actions workflows are included for:

- Maven build on every push and pull request
- Release creation with the executable JAR when a `v*` tag is pushed

## Screenshots

Place the required evidential screenshots in the root `screenshots/` folder before submission.
