package edu.miu.cs.cs489appsd.lab6.adsapp;

import edu.miu.cs.cs489appsd.lab6.adsapp.service.ClinicManagementService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class Lab6CommandLineRunner implements CommandLineRunner {

    private final ClinicManagementService clinicManagementService;

    public Lab6CommandLineRunner(ClinicManagementService clinicManagementService) {
        this.clinicManagementService = clinicManagementService;
    }

    @Override
    public void run(String... args) {
        clinicManagementService.seedSampleData();
        clinicManagementService.runCrudShowcase()
                .forEach(System.out::println);
    }
}
