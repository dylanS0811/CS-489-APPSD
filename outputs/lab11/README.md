# Lab11 - Testing with JUnit and Mockito

This lab adds JUnit and Mockito tests for the assignment PDF tasks and the Lab7 Web API testing tasks.

GitHub repository URL:

```text
https://github.com/dylanS0811/CS-489-APPSD
```

## Part 1 - ArrayFlattener

Implementation:

```text
src/main/java/edu/miu/cs/cs489appsd/lab11/arrays/ArrayFlattener.java
src/main/java/edu/miu/cs/cs489appsd/lab11/arrays/ArrayFlattenerService.java
```

JUnit test cases:

```text
src/test/java/edu/miu/cs/cs489appsd/lab11/arrays/ArrayFlattenerTestCases.java
```

Covered test cases:

- Valid 2-D nested array input: `{{1, 3}, {0}, {4, 5, 9}}` returns `{1, 3, 0, 4, 5, 9}`.
- Null input returns `null`.

## Part 1 - ArrayReversor

Implementation:

```text
src/main/java/edu/miu/cs/cs489appsd/lab11/arrays/ArrayReversor.java
```

JUnit and Mockito test cases:

```text
src/test/java/edu/miu/cs/cs489appsd/lab11/arrays/ArrayReversorTestCases.java
src/test/java/edu/miu/cs/cs489appsd/lab11/arrays/Lab11ArrayTestSuiteTest.java
```

Covered test cases:

- Valid 2-D nested array input returns the reversed flattened array `{9, 5, 4, 0, 3, 1}`.
- Null input returns `null`.
- Both test cases verify that the mocked `ArrayFlattenerService.flattenArray(...)` method is invoked.

## Part 2 - Lab7 Web API Tests

Patient service integration tests:

```text
src/test/java/edu/miu/cs/cs489appsd/lab7/adswebapi/PatientApiServiceFindPatientByIdIntegrationTests.java
```

Covered test cases:

- Existing patient ID returns patient data.
- Invalid patient ID throws `PatientNotFoundException`.

Patient controller unit test with mocked service:

```text
src/test/java/edu/miu/cs/cs489appsd/lab7/adswebapi/controller/PatientControllerUnitTests.java
```

Covered test case:

- `GET /adsweb/api/v1/patients` returns all patient data from a mocked `PatientApiService`.

## Verification

Run the complete test suite:

```bash
mvn -q test
```

Local verification completed:

```text
mvn -q test -> passed
```

Surefire reports include:

```text
ArrayFlattenerTestCases
ArrayReversorTestCases
Lab11ArrayTestSuiteTest
PatientApiServiceFindPatientByIdIntegrationTests
PatientControllerUnitTests
```
