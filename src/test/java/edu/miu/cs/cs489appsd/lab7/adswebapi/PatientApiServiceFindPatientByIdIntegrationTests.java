package edu.miu.cs.cs489appsd.lab7.adswebapi;

import edu.miu.cs.cs489appsd.lab6.adsapp.service.ClinicManagementService;
import edu.miu.cs.cs489appsd.lab7.adswebapi.dto.patient.PatientResponse;
import edu.miu.cs.cs489appsd.lab7.adswebapi.exception.PatientNotFoundException;
import edu.miu.cs.cs489appsd.lab7.adswebapi.service.PatientApiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = Lab7RestApiApplication.class)
@ActiveProfiles("test")
class PatientApiServiceFindPatientByIdIntegrationTests {

    @Autowired
    private PatientApiService patientApiService;

    @Autowired
    private ClinicManagementService clinicManagementService;

    @BeforeEach
    void setUp() {
        clinicManagementService.seedSampleData();
    }

    @Test
    void findPatientByIdWhenPatientExistsReturnsPatientData() {
        PatientResponse patientResponse = patientApiService.findPatientById(1L);

        assertEquals("P100", patientResponse.patientNumber());
        assertEquals("Gillian", patientResponse.firstName());
        assertEquals("White", patientResponse.lastName());
        assertEquals("Fairfield", patientResponse.primaryAddress().city());
    }

    @Test
    void findPatientByIdWhenPatientIdIsInvalidThrowsPatientNotFoundException() {
        PatientNotFoundException exception = assertThrows(
                PatientNotFoundException.class,
                () -> patientApiService.findPatientById(999L)
        );

        assertEquals("Patient with id 999 was not found", exception.getMessage());
    }
}
