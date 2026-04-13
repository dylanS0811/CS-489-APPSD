package edu.miu.cs.cs489appsd.lab7b.adsgraphqlapi.graphql.input;

public record NewAddressInput(
        String street,
        String city,
        String state,
        String zipCode
) {
}
