package edu.miu.cs.cs489appsd.lab7.adswebapi.service;

import edu.miu.cs.cs489appsd.lab7.adswebapi.dto.patient.PatientRequest;
import edu.miu.cs.cs489appsd.lab7.adswebapi.dto.patient.PatientResponse;

import java.util.List;

public interface PatientApiService {

    List<PatientResponse> getAllPatients();

    PatientResponse getPatientById(Long patientId);

    PatientResponse createPatient(PatientRequest patientRequest);

    PatientResponse updatePatient(Long patientId, PatientRequest patientRequest);

    void deletePatient(Long patientId);

    List<PatientResponse> searchPatients(String searchString);
}
