package com.tecsup.app.micro.user.domain.model;

import java.time.Instant;
import java.util.UUID;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Address {
    private final UUID id;
    private final UUID userId;

    private String label;
    private String line1;
    private String line2;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String reference;
    private boolean isDefault;

    private final Instant createdAt;
    private Instant updatedAt;

    public static Address create(UUID userId, String label, String line1, String line2, String city, String state,
            String country, String postalCode, String reference, boolean isDefault) {
        Instant now = Instant.now();
        return Address.builder()
                .id(UUID.randomUUID())
                .userId(userId)
                .label(label)
                .line1(line1)
                .line2(line2)
                .city(city)
                .state(state)
                .country(country)
                .postalCode(postalCode)
                .reference(reference)
                .isDefault(isDefault)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    public void update(String label, String line1, String line2, String city, String state, String country,
            String postalCode, String reference, Boolean isDefault) {
        if (label != null)
            this.label = label;
        if (line1 != null)
            this.line1 = line1;
        this.line2 = line2;
        if (city != null)
            this.city = city;
        if (state != null)
            this.state = state;
        if (country != null)
            this.country = country;
        this.postalCode = postalCode;
        this.reference = reference;
        if (isDefault != null)
            this.isDefault = isDefault;
        this.updatedAt = Instant.now();
    }
}