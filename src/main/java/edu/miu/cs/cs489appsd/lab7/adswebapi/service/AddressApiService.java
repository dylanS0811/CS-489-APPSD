package edu.miu.cs.cs489appsd.lab7.adswebapi.service;

import edu.miu.cs.cs489appsd.lab7.adswebapi.dto.address.AddressWithPatientResponse;

import java.util.List;

public interface AddressApiService {

    List<AddressWithPatientResponse> getAllAddresses();
}
