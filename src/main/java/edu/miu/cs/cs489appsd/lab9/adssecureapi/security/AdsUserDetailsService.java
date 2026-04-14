package edu.miu.cs.cs489appsd.lab9.adssecureapi.security;

import edu.miu.cs.cs489appsd.lab6.adsapp.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdsUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public AdsUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(AdsUserDetails::fromUser)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User '%s' was not found".formatted(username)));
    }
}
