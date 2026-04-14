package edu.miu.cs.cs489appsd.lab9.adssecureapi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.cs.cs489appsd.lab7.adswebapi.dto.address.AddressRequest;
import edu.miu.cs.cs489appsd.lab7.adswebapi.dto.patient.PatientRequest;
import edu.miu.cs.cs489appsd.lab9.adssecureapi.dto.auth.LoginRequest;
import edu.miu.cs.cs489appsd.lab9.adssecureapi.service.Lab9SecuritySeedService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Lab9SecureWebApiApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class Lab9SecureWebApiIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Lab9SecuritySeedService lab9SecuritySeedService;

    @BeforeEach
    void setUp() {
        lab9SecuritySeedService.seedSecureSampleData();
    }

    @Test
    void shouldAuthenticateAndReturnJwtToken() throws Exception {
        mockMvc.perform(post("/adsweb/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new LoginRequest("olivia.morgan", "welcome1"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").isString())
                .andExpect(jsonPath("$.tokenType").value("Bearer"))
                .andExpect(jsonPath("$.username").value("olivia.morgan"))
                .andExpect(jsonPath("$.role").value("OFFICE_MANAGER"));
    }

    @Test
    void shouldRequireAuthenticationForPatientsEndpoint() throws Exception {
        mockMvc.perform(get("/adsweb/api/v1/patients"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message", containsString("Authentication is required")));
    }

    @Test
    void shouldAllowOfficeManagerToReadButNotCreatePatients() throws Exception {
        String officeManagerToken = loginAndGetToken("olivia.morgan", "welcome1");

        mockMvc.perform(get("/adsweb/api/v1/patients")
                        .header(HttpHeaders.AUTHORIZATION, bearer(officeManagerToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));

        PatientRequest patientRequest = new PatientRequest(
                "P130",
                "Maria",
                "Anderson",
                "641-451-3130",
                "maria.anderson@example.com",
                LocalDate.of(1992, 8, 14),
                new AddressRequest("130 Elm St", "Ottumwa", "IA", "52501")
        );

        mockMvc.perform(post("/adsweb/api/v1/patients")
                        .header(HttpHeaders.AUTHORIZATION, bearer(officeManagerToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientRequest)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message", containsString("permission")));
    }

    @Test
    void shouldAllowAdministratorToCreatePatientAndViewProfile() throws Exception {
        String administratorToken = loginAndGetToken("ethan.reed", "welcome1");

        mockMvc.perform(get("/adsweb/api/v1/auth/me")
                        .header(HttpHeaders.AUTHORIZATION, bearer(administratorToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("ethan.reed"))
                .andExpect(jsonPath("$.role").value("ADMINISTRATOR"));

        PatientRequest patientRequest = new PatientRequest(
                "P130",
                "Maria",
                "Anderson",
                "641-451-3130",
                "maria.anderson@example.com",
                LocalDate.of(1992, 8, 14),
                new AddressRequest("130 Elm St", "Ottumwa", "IA", "52501")
        );

        mockMvc.perform(post("/adsweb/api/v1/patients")
                        .header(HttpHeaders.AUTHORIZATION, bearer(administratorToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.patientNumber").value("P130"))
                .andExpect(jsonPath("$.primaryAddress.city").value("Ottumwa"));
    }

    private String loginAndGetToken(String username, String password) throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/adsweb/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new LoginRequest(username, password))))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode response = objectMapper.readTree(mvcResult.getResponse().getContentAsString());
        return response.get("accessToken").asText();
    }

    private String bearer(String token) {
        return "Bearer " + token;
    }
}
