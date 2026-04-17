package edu.miu.cs.cs489appsd.lab7.adswebapi.controller;

import edu.miu.cs.cs489appsd.lab7.adswebapi.dto.address.AddressResponse;
import edu.miu.cs.cs489appsd.lab7.adswebapi.dto.patient.PatientResponse;
import edu.miu.cs.cs489appsd.lab7.adswebapi.service.PatientApiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PatientController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = PatientController.class)
class PatientControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientApiService patientApiService;

    @Test
    void getAllPatientsEndpointReturnsAllPatientsFromMockedService() throws Exception {
        List<PatientResponse> patients = List.of(
                new PatientResponse(
                        1L,
                        "P100",
                        "Gillian",
                        "White",
                        "641-451-3000",
                        "gillian.white@example.com",
                        LocalDate.of(1989, 4, 12),
                        new AddressResponse(4L, "100 Maple St", "Fairfield", "IA", "52556")
                ),
                new PatientResponse(
                        2L,
                        "P105",
                        "Jill",
                        "Bell",
                        "641-451-3005",
                        "jill.bell@example.com",
                        LocalDate.of(1991, 7, 21),
                        new AddressResponse(5L, "105 Cedar Ave", "Fairfield", "IA", "52556")
                )
        );
        when(patientApiService.getAllPatients()).thenReturn(patients);

        mockMvc.perform(get("/adsweb/api/v1/patients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].patientNumber").value("P100"))
                .andExpect(jsonPath("$[0].primaryAddress.street").value("100 Maple St"))
                .andExpect(jsonPath("$[1].patientNumber").value("P105"));

        verify(patientApiService).getAllPatients();
    }
}
