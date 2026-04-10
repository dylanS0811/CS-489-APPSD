package edu.miu.cs.cs489appsd.lab7.adswebapi.dto.patient;

import java.time.LocalDate;

public record PatientSummaryResponse(
        Long patientId,
        String patientNumber,
        String firstName,
        String lastName,
        String phoneNumber,
        String email,
        LocalDate dateOfBirth
) {
}
