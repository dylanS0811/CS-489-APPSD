package edu.miu.cs.cs489appsd.lab6.adsapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = {
        SecurityAutoConfiguration.class,
        UserDetailsServiceAutoConfiguration.class
})
public class Lab6DataPersistenceApplication {

    public static void main(String[] args) {
        SpringApplication.run(Lab6DataPersistenceApplication.class, args);
    }
}
