package edu.miu.cs.cs489appsd.lab7b.adsgraphqlapi.graphql.input;

public record NewPatientInput(
        String patientNumber,
        String firstName,
        String lastName,
        String phoneNumber,
        String email,
        String dateOfBirth,
        NewAddressInput primaryAddress
) {
}
