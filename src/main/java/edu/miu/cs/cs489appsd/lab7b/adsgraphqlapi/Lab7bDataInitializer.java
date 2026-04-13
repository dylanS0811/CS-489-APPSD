package edu.miu.cs.cs489appsd.lab7b.adsgraphqlapi;

import edu.miu.cs.cs489appsd.lab6.adsapp.service.ClinicManagementService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class Lab7bDataInitializer implements ApplicationRunner {

    private final ClinicManagementService clinicManagementService;

    public Lab7bDataInitializer(ClinicManagementService clinicManagementService) {
        this.clinicManagementService = clinicManagementService;
    }

    @Override
    public void run(ApplicationArguments args) {
        clinicManagementService.seedSampleData();
    }
}
