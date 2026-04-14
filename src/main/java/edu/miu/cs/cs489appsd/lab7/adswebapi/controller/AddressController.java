package edu.miu.cs.cs489appsd.lab7.adswebapi.controller;

import edu.miu.cs.cs489appsd.lab7.adswebapi.dto.address.AddressWithPatientResponse;
import edu.miu.cs.cs489appsd.lab7.adswebapi.service.AddressApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/adsweb/api/v1")
public class AddressController {

    private final AddressApiService addressApiService;

    public AddressController(AddressApiService addressApiService) {
        this.addressApiService = addressApiService;
    }

    @GetMapping("/addresses")
    @PreAuthorize("hasAnyRole('OFFICE_MANAGER', 'ADMINISTRATOR')")
    public ResponseEntity<List<AddressWithPatientResponse>> getAllAddresses() {
        return ResponseEntity.ok(addressApiService.getAllAddresses());
    }
}
