package edu.miu.cs.cs489appsd.lab7b.adsgraphqlapi.controller;

import edu.miu.cs.cs489appsd.lab7.adswebapi.dto.address.AddressRequest;
import edu.miu.cs.cs489appsd.lab7.adswebapi.dto.address.AddressWithPatientResponse;
import edu.miu.cs.cs489appsd.lab7.adswebapi.dto.patient.PatientRequest;
import edu.miu.cs.cs489appsd.lab7.adswebapi.dto.patient.PatientResponse;
import edu.miu.cs.cs489appsd.lab7.adswebapi.exception.PatientNotFoundException;
import edu.miu.cs.cs489appsd.lab7.adswebapi.service.AddressApiService;
import edu.miu.cs.cs489appsd.lab7.adswebapi.service.PatientApiService;
import edu.miu.cs.cs489appsd.lab7b.adsgraphqlapi.graphql.input.NewPatientInput;
import edu.miu.cs.cs489appsd.lab7b.adsgraphqlapi.graphql.payload.DeletePatientPayload;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.List;

@Controller
public class PatientGraphqlController {

    private final PatientApiService patientApiService;
    private final AddressApiService addressApiService;

    public PatientGraphqlController(PatientApiService patientApiService,
                                    AddressApiService addressApiService) {
        this.patientApiService = patientApiService;
        this.addressApiService = addressApiService;
    }

    @QueryMapping
    public List<PatientResponse> allPatients() {
        return patientApiService.getAllPatients();
    }

    @QueryMapping
    public PatientResponse patientById(@Argument Long patientId) {
        try {
            return patientApiService.getPatientById(patientId);
        } catch (PatientNotFoundException exception) {
            return null;
        }
    }

    @QueryMapping
    public List<PatientResponse> searchPatients(@Argument String searchString) {
        return patientApiService.searchPatients(searchString);
    }

    @QueryMapping
    public List<AddressWithPatientResponse> allAddresses() {
        return addressApiService.getAllAddresses();
    }

    @MutationMapping
    public PatientResponse addNewPatient(@Argument NewPatientInput newPatient) {
        return patientApiService.createPatient(toPatientRequest(newPatient));
    }

    @MutationMapping
    public PatientResponse updatePatient(@Argument Long patientId,
                                         @Argument NewPatientInput editedPatient) {
        return patientApiService.updatePatient(patientId, toPatientRequest(editedPatient));
    }

    @MutationMapping
    public DeletePatientPayload deletePatient(@Argument Long patientId) {
        try {
            patientApiService.deletePatient(patientId);
            return new DeletePatientPayload(patientId, true, "Patient deleted successfully");
        } catch (PatientNotFoundException exception) {
            return new DeletePatientPayload(patientId, false, exception.getMessage());
        }
    }

    private PatientRequest toPatientRequest(NewPatientInput input) {
        return new PatientRequest(
                input.patientNumber(),
                input.firstName(),
                input.lastName(),
                input.phoneNumber(),
                input.email(),
                LocalDate.parse(input.dateOfBirth()),
                new AddressRequest(
                        input.primaryAddress().street(),
                        input.primaryAddress().city(),
                        input.primaryAddress().state(),
                        input.primaryAddress().zipCode()
                )
        );
    }
}
