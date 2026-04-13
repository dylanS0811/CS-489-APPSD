package edu.miu.cs.cs489appsd.lab7b.adsgraphqlapi;

import edu.miu.cs.cs489appsd.lab6.adsapp.model.Patient;
import edu.miu.cs.cs489appsd.lab6.adsapp.repository.PatientRepository;
import edu.miu.cs.cs489appsd.lab6.adsapp.service.ClinicManagementService;
import edu.miu.cs.cs489appsd.lab7.adswebapi.service.impl.PatientApiServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackageClasses = {
        Lab7bGraphqlWebApiApplication.class,
        ClinicManagementService.class,
        PatientApiServiceImpl.class
})
@EntityScan(basePackageClasses = Patient.class)
@EnableJpaRepositories(basePackageClasses = PatientRepository.class)
public class Lab7bGraphqlWebApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(Lab7bGraphqlWebApiApplication.class, args);
    }
}
