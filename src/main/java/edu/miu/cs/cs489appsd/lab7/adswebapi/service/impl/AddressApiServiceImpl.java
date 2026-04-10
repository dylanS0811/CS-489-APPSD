package edu.miu.cs.cs489appsd.lab7.adswebapi.service.impl;

import edu.miu.cs.cs489appsd.lab7.adswebapi.dto.address.AddressWithPatientResponse;
import edu.miu.cs.cs489appsd.lab7.adswebapi.dto.patient.PatientSummaryResponse;
import edu.miu.cs.cs489appsd.lab6.adsapp.model.Patient;
import edu.miu.cs.cs489appsd.lab6.adsapp.repository.PatientRepository;
import edu.miu.cs.cs489appsd.lab7.adswebapi.service.AddressApiService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class AddressApiServiceImpl implements AddressApiService {

    private final PatientRepository patientRepository;

    public AddressApiServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public List<AddressWithPatientResponse> getAllAddresses() {
        return patientRepository.findAll()
                .stream()
                .sorted(Comparator
                        .comparing((Patient patient) -> patient.getMailingAddress().getCity(), String.CASE_INSENSITIVE_ORDER)
                        .thenComparing(Patient::getLastName, String.CASE_INSENSITIVE_ORDER)
                        .thenComparing(Patient::getFirstName, String.CASE_INSENSITIVE_ORDER))
                .map(patient -> new AddressWithPatientResponse(
                        patient.getMailingAddress().getAddressId(),
                        patient.getMailingAddress().getStreet(),
                        patient.getMailingAddress().getCity(),
                        patient.getMailingAddress().getState(),
                        patient.getMailingAddress().getZipCode(),
                        new PatientSummaryResponse(
                                patient.getPatientId(),
                                patient.getPatientNumber(),
                                patient.getFirstName(),
                                patient.getLastName(),
                                patient.getPhoneNumber(),
                                patient.getEmail(),
                                patient.getDateOfBirth()
                        )
                ))
                .toList();
    }
}
