package edu.miu.cs.cs489appsd.lab7.adswebapi.dto.patient;

import edu.miu.cs.cs489appsd.lab7.adswebapi.dto.address.AddressResponse;

import java.time.LocalDate;

public record PatientResponse(
        Long patientId,
        String patientNumber,
        String firstName,
        String lastName,
        String phoneNumber,
        String email,
        LocalDate dateOfBirth,
        AddressResponse primaryAddress
) {
}
