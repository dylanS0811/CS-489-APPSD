package edu.miu.cs.cs489appsd.lab7.adswebapi.dto.address;

import edu.miu.cs.cs489appsd.lab7.adswebapi.dto.patient.PatientSummaryResponse;

public record AddressWithPatientResponse(
        Long addressId,
        String street,
        String city,
        String state,
        String zipCode,
        PatientSummaryResponse patient
) {
}
