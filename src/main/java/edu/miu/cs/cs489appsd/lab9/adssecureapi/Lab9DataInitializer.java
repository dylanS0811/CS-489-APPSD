package edu.miu.cs.cs489appsd.lab9.adssecureapi;

import edu.miu.cs.cs489appsd.lab9.adssecureapi.service.Lab9SecuritySeedService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class Lab9DataInitializer implements ApplicationRunner {

    private final Lab9SecuritySeedService lab9SecuritySeedService;

    public Lab9DataInitializer(Lab9SecuritySeedService lab9SecuritySeedService) {
        this.lab9SecuritySeedService = lab9SecuritySeedService;
    }

    @Override
    public void run(ApplicationArguments args) {
        lab9SecuritySeedService.seedSecureSampleData();
    }
}
