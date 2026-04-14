package edu.miu.cs.cs489appsd.lab7.adswebapi.controller;

import edu.miu.cs.cs489appsd.lab7.adswebapi.dto.patient.PatientRequest;
import edu.miu.cs.cs489appsd.lab7.adswebapi.dto.patient.PatientResponse;
import edu.miu.cs.cs489appsd.lab7.adswebapi.service.PatientApiService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/adsweb/api/v1")
public class PatientController {

    private final PatientApiService patientApiService;

    public PatientController(PatientApiService patientApiService) {
        this.patientApiService = patientApiService;
    }

    @GetMapping("/patients")
    @PreAuthorize("hasAnyRole('OFFICE_MANAGER', 'ADMINISTRATOR')")
    public ResponseEntity<List<PatientResponse>> getAllPatients() {
        return ResponseEntity.ok(patientApiService.getAllPatients());
    }

    @GetMapping({"/patients/{patientId}", "/patient/{patientId}"})
    @PreAuthorize("hasAnyRole('OFFICE_MANAGER', 'ADMINISTRATOR')")
    public ResponseEntity<PatientResponse> getPatientById(@PathVariable Long patientId) {
        return ResponseEntity.ok(patientApiService.getPatientById(patientId));
    }

    @PostMapping({"/patients", "/patient"})
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<PatientResponse> createPatient(@Valid @RequestBody PatientRequest patientRequest) {
        return new ResponseEntity<>(patientApiService.createPatient(patientRequest), HttpStatus.CREATED);
    }

    @PutMapping({"/patients/{patientId}", "/patient/{patientId}"})
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<PatientResponse> updatePatient(@PathVariable Long patientId,
                                                         @Valid @RequestBody PatientRequest patientRequest) {
        return ResponseEntity.ok(patientApiService.updatePatient(patientId, patientRequest));
    }

    @DeleteMapping({"/patients/{patientId}", "/patient/{patientId}"})
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<Void> deletePatient(@PathVariable Long patientId) {
        patientApiService.deletePatient(patientId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping({"/patient/search/{searchString}", "/patients/search/{searchString}"})
    @PreAuthorize("hasAnyRole('OFFICE_MANAGER', 'ADMINISTRATOR')")
    public ResponseEntity<List<PatientResponse>> searchPatients(@PathVariable String searchString) {
        return ResponseEntity.ok(patientApiService.searchPatients(searchString));
    }
}
