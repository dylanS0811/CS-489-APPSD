# CS-489-APPSD

Maven-based Java CLI solutions and submission assets for:

- Lab 1: Product Management app
- Lab 2A: Employee Pension Plans app
- Lab 2B: Patient Appointment Management System (PAMS)
- Lab 3: Software Requirements Discovery and Domain Modeling for Advantis Dental Surgeries (ADS)
- Lab 4: Software Solution Architecture for Advantis Dental Surgeries (ADS)
- Lab 6: Data Persistence for Advantis Dental Surgeries (ADS)

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

## Run Lab 6

Build the project:

```bash
mvn clean package
```

Run the Lab 6 executable JAR:

```bash
java -jar target/cs489-appsd.jar
```

Before running the JAR, make sure a local MySQL server is running and accepting connections on port `3306`.

## Test Lab 6

Run the automated test that verifies the Spring Boot application context loads correctly:

```bash
mvn test
```

You can also verify the packaged application directly:

```bash
mvn -DskipTests package
java -jar target/cs489-appsd.jar
```

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

## MySQL Configuration For Lab 6

Lab 6 now uses a real local MySQL server for both runtime and test execution. H2 has been removed from the project.

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

The MySQL test-profile configuration is stored in:

```text
src/test/resources/application-test.properties
```

The Lab 6 sample data is cleared and reseeded on each application run so the console output stays consistent while using a real MySQL database.

## CI/CD

GitHub Actions workflows are included for:

- Maven build on every push and pull request
- Release creation with the executable JAR when a `v*` tag is pushed

## Screenshots

Place the required evidential screenshots in the root `screenshots/` folder before submission.
