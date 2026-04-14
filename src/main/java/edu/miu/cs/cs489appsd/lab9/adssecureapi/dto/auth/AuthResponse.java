package edu.miu.cs.cs489appsd.lab9.adssecureapi.dto.auth;

public record AuthResponse(
        String accessToken,
        String tokenType,
        long expiresIn,
        String username,
        String fullName,
        String role
) {
}
