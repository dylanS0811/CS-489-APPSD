package edu.miu.cs.cs489appsd.lab9.adssecureapi.service;

import edu.miu.cs.cs489appsd.lab6.adsapp.model.User;
import edu.miu.cs.cs489appsd.lab6.adsapp.repository.UserRepository;
import edu.miu.cs.cs489appsd.lab6.adsapp.service.ClinicManagementService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class Lab9SecuritySeedService {

    private final ClinicManagementService clinicManagementService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Lab9SecuritySeedService(ClinicManagementService clinicManagementService,
                                   UserRepository userRepository,
                                   PasswordEncoder passwordEncoder) {
        this.clinicManagementService = clinicManagementService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void seedSecureSampleData() {
        clinicManagementService.seedSampleData();

        List<User> users = userRepository.findAll();
        users.forEach(user -> user.setPassword(passwordEncoder.encode(user.getPassword())));
        userRepository.saveAll(users);
    }
}
