package edu.miu.cs.cs489appsd.lab7.adswebapi.service.impl;

import edu.miu.cs.cs489appsd.lab7.adswebapi.dto.address.AddressRequest;
import edu.miu.cs.cs489appsd.lab7.adswebapi.dto.address.AddressResponse;
import edu.miu.cs.cs489appsd.lab7.adswebapi.dto.patient.PatientRequest;
import edu.miu.cs.cs489appsd.lab7.adswebapi.dto.patient.PatientResponse;
import edu.miu.cs.cs489appsd.lab7.adswebapi.exception.PatientNotFoundException;
import edu.miu.cs.cs489appsd.lab6.adsapp.model.Address;
import edu.miu.cs.cs489appsd.lab6.adsapp.model.Patient;
import edu.miu.cs.cs489appsd.lab6.adsapp.repository.AppointmentRepository;
import edu.miu.cs.cs489appsd.lab6.adsapp.repository.PatientRepository;
import edu.miu.cs.cs489appsd.lab7.adswebapi.service.PatientApiService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PatientApiServiceImpl implements PatientApiService {

    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;

    public PatientApiServiceImpl(PatientRepository patientRepository,
                                 AppointmentRepository appointmentRepository) {
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PatientResponse> getAllPatients() {
        return patientRepository.findAllByOrderByLastNameAscFirstNameAsc()
                .stream()
                .map(this::toPatientResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PatientResponse getPatientById(Long patientId) {
        return toPatientResponse(findPatient(patientId));
    }

    @Override
    public PatientResponse createPatient(PatientRequest patientRequest) {
        Patient patient = new Patient(
                normalize(patientRequest.patientNumber()),
                normalize(patientRequest.firstName()),
                normalize(patientRequest.lastName()),
                normalize(patientRequest.phoneNumber()),
                normalize(patientRequest.email()),
                patientRequest.dateOfBirth(),
                toAddress(patientRequest.primaryAddress())
        );
        return toPatientResponse(patientRepository.save(patient));
    }

    @Override
    public PatientResponse updatePatient(Long patientId, PatientRequest patientRequest) {
        Patient patient = findPatient(patientId);
        patient.setPatientNumber(normalize(patientRequest.patientNumber()));
        patient.setFirstName(normalize(patientRequest.firstName()));
        patient.setLastName(normalize(patientRequest.lastName()));
        patient.setPhoneNumber(normalize(patientRequest.phoneNumber()));
        patient.setEmail(normalize(patientRequest.email()));
        patient.setDateOfBirth(patientRequest.dateOfBirth());

        Address existingAddress = patient.getMailingAddress();
        AddressRequest addressRequest = patientRequest.primaryAddress();
        existingAddress.setStreet(normalize(addressRequest.street()));
        existingAddress.setCity(normalize(addressRequest.city()));
        existingAddress.setState(normalize(addressRequest.state()));
        existingAddress.setZipCode(normalize(addressRequest.zipCode()));

        return toPatientResponse(patientRepository.save(patient));
    }

    @Override
    public void deletePatient(Long patientId) {
        Patient patient = findPatient(patientId);
        appointmentRepository.deleteAllByPatient(patient);
        patientRepository.delete(patient);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PatientResponse> searchPatients(String searchString) {
        return patientRepository.searchPatients(normalize(searchString))
                .stream()
                .map(this::toPatientResponse)
                .toList();
    }

    private Patient findPatient(Long patientId) {
        return patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException(
                        "Patient with id %d was not found".formatted(patientId)));
    }

    private PatientResponse toPatientResponse(Patient patient) {
        return new PatientResponse(
                patient.getPatientId(),
                patient.getPatientNumber(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getPhoneNumber(),
                patient.getEmail(),
                patient.getDateOfBirth(),
                toAddressResponse(patient.getMailingAddress())
        );
    }

    private Address toAddress(AddressRequest addressRequest) {
        return new Address(
                normalize(addressRequest.street()),
                normalize(addressRequest.city()),
                normalize(addressRequest.state()),
                normalize(addressRequest.zipCode())
        );
    }

    private AddressResponse toAddressResponse(Address address) {
        return new AddressResponse(
                address.getAddressId(),
                address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getZipCode()
        );
    }

    private String normalize(String value) {
        return value == null ? null : value.trim();
    }
}
