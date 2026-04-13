package edu.miu.cs.cs489appsd.lab7b.adsgraphqlapi;

import edu.miu.cs.cs489appsd.lab6.adsapp.service.ClinicManagementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.ExecutionGraphQlService;
import org.springframework.graphql.test.tester.ExecutionGraphQlServiceTester;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = Lab7bGraphqlWebApiApplication.class)
@ActiveProfiles("test")
class Lab7bGraphqlWebApiIntegrationTests {

    @Autowired
    private ClinicManagementService clinicManagementService;

    @Autowired
    private ExecutionGraphQlService executionGraphQlService;

    private GraphQlTester graphQlTester;

    @BeforeEach
    void setUp() {
        clinicManagementService.seedSampleData();
        graphQlTester = ExecutionGraphQlServiceTester.create(executionGraphQlService);
    }

    @Test
    void shouldQueryAllPatients() {
        graphQlTester.document("""
                        query {
                          allPatients {
                            patientId
                            lastName
                            primaryAddress {
                              street
                            }
                          }
                        }
                        """)
                .execute()
                .path("allPatients").entityList(Object.class).hasSize(4)
                .path("allPatients[0].lastName").entity(String.class).isEqualTo("Bell")
                .path("allPatients[0].primaryAddress.street").entity(String.class).isEqualTo("105 Cedar Ave");
    }

    @Test
    void shouldQueryPatientById() {
        graphQlTester.document("""
                        query {
                          patientById(patientId: 1) {
                            patientNumber
                            email
                          }
                        }
                        """)
                .execute()
                .path("patientById.patientNumber").entity(String.class).isEqualTo("P100")
                .path("patientById.email").entity(String.class).isEqualTo("gillian.white@example.com");
    }

    @Test
    void shouldAddUpdateSearchAndDeletePatient() {
        graphQlTester.document("""
                        mutation {
                          addNewPatient(newPatient: {
                            patientNumber: "P130",
                            firstName: "Maria",
                            lastName: "Anderson",
                            phoneNumber: "641-451-3130",
                            email: "maria.anderson@example.com",
                            dateOfBirth: "1992-08-14",
                            primaryAddress: {
                              street: "130 Elm St",
                              city: "Ottumwa",
                              state: "IA",
                              zipCode: "52501"
                            }
                          }) {
                            patientId
                            patientNumber
                            primaryAddress {
                              city
                            }
                          }
                        }
                        """)
                .execute()
                .path("addNewPatient.patientId").entity(String.class).isEqualTo("5")
                .path("addNewPatient.patientNumber").entity(String.class).isEqualTo("P130")
                .path("addNewPatient.primaryAddress.city").entity(String.class).isEqualTo("Ottumwa");

        graphQlTester.document("""
                        mutation {
                          updatePatient(patientId: 5, editedPatient: {
                            patientNumber: "P130",
                            firstName: "Maria",
                            lastName: "Anders",
                            phoneNumber: "641-451-3131",
                            email: "maria.anders@example.com",
                            dateOfBirth: "1992-08-14",
                            primaryAddress: {
                              street: "130 Updated Elm St",
                              city: "Ottumwa",
                              state: "IA",
                              zipCode: "52501"
                            }
                          }) {
                            lastName
                            phoneNumber
                            primaryAddress {
                              street
                            }
                          }
                        }
                        """)
                .execute()
                .path("updatePatient.lastName").entity(String.class).isEqualTo("Anders")
                .path("updatePatient.phoneNumber").entity(String.class).isEqualTo("641-451-3131")
                .path("updatePatient.primaryAddress.street").entity(String.class).isEqualTo("130 Updated Elm St");

        graphQlTester.document("""
                        query {
                          searchPatients(searchString: "Ottumwa") {
                            patientNumber
                          }
                        }
                        """)
                .execute()
                .path("searchPatients").entityList(Object.class).hasSize(1)
                .path("searchPatients[0].patientNumber").entity(String.class).isEqualTo("P130");

        graphQlTester.document("""
                        mutation {
                          deletePatient(patientId: 5) {
                            patientId
                            deleted
                          }
                        }
                        """)
                .execute()
                .path("deletePatient.patientId").entity(String.class).isEqualTo("5")
                .path("deletePatient.deleted").entity(Boolean.class).isEqualTo(true);
    }

    @Test
    void shouldQueryAllAddresses() {
        graphQlTester.document("""
                        query {
                          allAddresses {
                            city
                            patient {
                              patientNumber
                            }
                          }
                        }
                        """)
                .execute()
                .path("allAddresses").entityList(Object.class).hasSize(4)
                .path("allAddresses[0].patient.patientNumber").entity(String.class).isEqualTo("P105");
    }
}
