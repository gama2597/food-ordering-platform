package com.tecsup.app.micro.user.presentation.dto;

import java.time.Instant;
import java.util.UUID;

public record AddressResponse(
        UUID id,
        String label,
        String line1,
        String line2,
        String city,
        String state,
        String country,
        String postalCode,
        String reference,
        boolean isDefault,
        Instant createdAt,
        Instant updatedAt) {
}