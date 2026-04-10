package edu.miu.cs.cs489appsd.lab7.adswebapi.dto.address;

public record AddressResponse(
        Long addressId,
        String street,
        String city,
        String state,
        String zipCode
) {
}
