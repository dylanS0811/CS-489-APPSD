package edu.miu.cs.cs489appsd.lab7b.adsgraphqlapi.graphql.payload;

public record DeletePatientPayload(
        Long patientId,
        boolean deleted,
        String message
) {
}
