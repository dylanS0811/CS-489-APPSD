package edu.miu.cs.cs489appsd.lab7.adswebapi;

import edu.miu.cs.cs489appsd.lab6.adsapp.model.Patient;
import edu.miu.cs.cs489appsd.lab6.adsapp.repository.PatientRepository;
import edu.miu.cs.cs489appsd.lab6.adsapp.service.ClinicManagementService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
        scanBasePackageClasses = {
        Lab7RestApiApplication.class,
        ClinicManagementService.class
},
        exclude = {
                SecurityAutoConfiguration.class,
                UserDetailsServiceAutoConfiguration.class
        }
)
@EntityScan(basePackageClasses = Patient.class)
@EnableJpaRepositories(basePackageClasses = PatientRepository.class)
public class Lab7RestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(Lab7RestApiApplication.class, args);
    }
}
