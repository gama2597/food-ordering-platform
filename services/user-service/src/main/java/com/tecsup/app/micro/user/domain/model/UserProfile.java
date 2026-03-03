package com.tecsup.app.micro.user.domain.model;

import java.time.Instant;
import java.util.UUID;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserProfile {
    private final UUID id;
    private String username;
    private String email;
    private String fullName;
    private String phone;
    private final Instant createdAt;
    private Instant updatedAt;

    public static UserProfile provision(UUID id, String username, String email, String fullName) {
        Instant now = Instant.now();
        return UserProfile.builder()
                .id(id)
                .username(username)
                .email(email)
                .fullName(fullName)
                .phone(null)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    public void updateProfile(String fullName, String phone) {
        if (fullName == null || fullName.isBlank())
            throw new IllegalArgumentException("fullName is required");
        this.fullName = fullName;
        this.phone = phone;
        this.updatedAt = Instant.now();
    }
}