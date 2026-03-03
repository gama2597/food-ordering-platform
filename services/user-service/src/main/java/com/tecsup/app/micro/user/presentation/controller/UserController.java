package com.tecsup.app.micro.user.presentation.controller;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @GetMapping("/me")
    public UserProfileResponse me(@AuthenticationPrincipal Jwt jwt, Authentication auth) {

        UUID id = UUID.fromString(jwt.getSubject()); // sub suele ser UUID
        String username = jwt.getClaimAsString("preferred_username");
        String email = jwt.getClaimAsString("email");
        String fullName = jwt.getClaimAsString("name");

        List<String> roles = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(r -> r.startsWith("ROLE_"))
                .sorted()
                .toList();

        Instant now = Instant.now();

        return new UserProfileResponse(
                id,
                username,
                email,
                fullName != null ? fullName : username,
                null,
                roles,
                now,
                now);
    }

    public record UserProfileResponse(
            UUID id,
            String username,
            String email,
            String fullName,
            String phone,
            List<String> roles,
            Instant createdAt,
            Instant updatedAt) {
    }
}