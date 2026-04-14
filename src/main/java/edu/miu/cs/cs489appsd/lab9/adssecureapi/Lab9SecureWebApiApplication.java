package edu.miu.cs.cs489appsd.lab9.adssecureapi;

import edu.miu.cs.cs489appsd.lab6.adsapp.model.Patient;
import edu.miu.cs.cs489appsd.lab6.adsapp.repository.PatientRepository;
import edu.miu.cs.cs489appsd.lab6.adsapp.service.ClinicManagementService;
import edu.miu.cs.cs489appsd.lab7.adswebapi.advice.AdsWebApiExceptionHandler;
import edu.miu.cs.cs489appsd.lab7.adswebapi.controller.AddressController;
import edu.miu.cs.cs489appsd.lab7.adswebapi.controller.PatientController;
import edu.miu.cs.cs489appsd.lab7.adswebapi.service.impl.AddressApiServiceImpl;
import edu.miu.cs.cs489appsd.lab7.adswebapi.service.impl.PatientApiServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackageClasses = {
        Lab9SecureWebApiApplication.class,
        ClinicManagementService.class,
        PatientController.class,
        AddressController.class,
        PatientApiServiceImpl.class,
        AddressApiServiceImpl.class,
        AdsWebApiExceptionHandler.class
})
@EntityScan(basePackageClasses = Patient.class)
@EnableJpaRepositories(basePackageClasses = PatientRepository.class)
public class Lab9SecureWebApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(Lab9SecureWebApiApplication.class, args);
    }
}
