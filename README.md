# CS-489-APPSD

Maven-based Java CLI solutions and submission assets for:

- Lab 1: Product Management app
- Lab 2A: Employee Pension Plans app
- Lab 2B: Patient Appointment Management System (PAMS)
- Lab 3: Software Requirements Discovery and Domain Modeling for Advantis Dental Surgeries (ADS)

Java version: 25

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
java -jar target/cs489-appsd.jar
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

## CI/CD

GitHub Actions workflows are included for:

- Maven build on every push and pull request
- Release creation with the executable JAR when a `v*` tag is pushed

## Screenshots

Place the required evidential screenshots in the root `screenshots/` folder before submission.
