package edu.miu.cs.cs489appsd.lab7.adswebapi;

import edu.miu.cs.cs489appsd.lab6.adsapp.service.ClinicManagementService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class Lab7DataInitializer implements ApplicationRunner {

    private final ClinicManagementService clinicManagementService;

    public Lab7DataInitializer(ClinicManagementService clinicManagementService) {
        this.clinicManagementService = clinicManagementService;
    }

    @Override
    public void run(ApplicationArguments args) {
        clinicManagementService.seedSampleData();
    }
}
