package edu.miu.cs.cs489appsd.lab7.adswebapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.cs.cs489appsd.lab6.adsapp.service.ClinicManagementService;
import edu.miu.cs.cs489appsd.lab7.adswebapi.dto.address.AddressRequest;
import edu.miu.cs.cs489appsd.lab7.adswebapi.dto.patient.PatientRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Lab7RestApiApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class Lab7RestApiIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClinicManagementService clinicManagementService;

    @BeforeEach
    void setUp() {
        clinicManagementService.seedSampleData();
    }

    @Test
    void shouldListPatientsSortedByLastName() throws Exception {
        mockMvc.perform(get("/adsweb/api/v1/patients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].lastName").value("Bell"))
                .andExpect(jsonPath("$[0].primaryAddress.street").value("105 Cedar Ave"))
                .andExpect(jsonPath("$[3].lastName").value("White"));
    }

    @Test
    void shouldReturnPatientById() throws Exception {
        mockMvc.perform(get("/adsweb/api/v1/patients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.patientNumber").value("P100"))
                .andExpect(jsonPath("$.primaryAddress.city").value("Fairfield"));
    }

    @Test
    void shouldRejectInvalidPatientId() throws Exception {
        mockMvc.perform(get("/adsweb/api/v1/patients/not-a-number"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("numeric id is required")));
    }

    @Test
    void shouldCreateUpdateSearchAndDeletePatient() throws Exception {
        PatientRequest createRequest = new PatientRequest(
                "P130",
                "Maria",
                "Anderson",
                "641-451-3130",
                "maria.anderson@example.com",
                LocalDate.of(1992, 8, 14),
                new AddressRequest("130 Elm St", "Ottumwa", "IA", "52501")
        );

        mockMvc.perform(post("/adsweb/api/v1/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.patientId").isNumber())
                .andExpect(jsonPath("$.patientNumber").value("P130"))
                .andExpect(jsonPath("$.primaryAddress.city").value("Ottumwa"));

        PatientRequest updateRequest = new PatientRequest(
                "P130",
                "Maria",
                "Anders",
                "641-451-3131",
                "maria.anders@example.com",
                LocalDate.of(1992, 8, 14),
                new AddressRequest("130 Updated Elm St", "Ottumwa", "IA", "52501")
        );

        mockMvc.perform(put("/adsweb/api/v1/patients/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value("Anders"))
                .andExpect(jsonPath("$.phoneNumber").value("641-451-3131"))
                .andExpect(jsonPath("$.primaryAddress.street").value("130 Updated Elm St"));

        mockMvc.perform(get("/adsweb/api/v1/patient/search/Ottumwa"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].patientNumber").value("P130"));

        mockMvc.perform(delete("/adsweb/api/v1/patient/5"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/adsweb/api/v1/patients/5"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldListAddressesWithPatientData() throws Exception {
        mockMvc.perform(get("/adsweb/api/v1/addresses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].patient.patientNumber").value("P105"))
                .andExpect(jsonPath("$[0].street").value("105 Cedar Ave"));
    }
}
