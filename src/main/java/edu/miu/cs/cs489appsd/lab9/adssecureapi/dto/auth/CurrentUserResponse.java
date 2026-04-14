package edu.miu.cs.cs489appsd.lab9.adssecureapi.dto.auth;

public record CurrentUserResponse(
        Long userId,
        String username,
        String fullName,
        String email,
        String role
) {
}
