package edu.miu.cs.cs489appsd.lab9.adssecureapi.controller;

import edu.miu.cs.cs489appsd.lab9.adssecureapi.dto.auth.AuthResponse;
import edu.miu.cs.cs489appsd.lab9.adssecureapi.dto.auth.CurrentUserResponse;
import edu.miu.cs.cs489appsd.lab9.adssecureapi.dto.auth.LoginRequest;
import edu.miu.cs.cs489appsd.lab9.adssecureapi.security.AdsUserDetails;
import edu.miu.cs.cs489appsd.lab9.adssecureapi.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/adsweb/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        AdsUserDetails authenticatedUser = (AdsUserDetails) authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        normalize(loginRequest.username()),
                        loginRequest.password()
                )
        ).getPrincipal();

        String token = jwtService.generateToken(authenticatedUser);
        return ResponseEntity.ok(new AuthResponse(
                token,
                "Bearer",
                jwtService.getExpirationMs(),
                authenticatedUser.getUsername(),
                authenticatedUser.getFullName(),
                authenticatedUser.getRoleName()
        ));
    }

    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('OFFICE_MANAGER', 'ADMINISTRATOR')")
    public ResponseEntity<CurrentUserResponse> currentUser(@AuthenticationPrincipal AdsUserDetails currentUser) {
        return ResponseEntity.ok(new CurrentUserResponse(
                currentUser.getUserId(),
                currentUser.getUsername(),
                currentUser.getFullName(),
                currentUser.getEmail(),
                currentUser.getRoleName()
        ));
    }

    private String normalize(String value) {
        return value == null ? null : value.trim();
    }
}
