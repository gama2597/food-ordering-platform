package com.tecsup.app.micro.user.presentation.dto;

import jakarta.validation.constraints.Size;

public record UpdateAddressRequest(
        @Size(max = 60) String label,
        @Size(max = 160) String line1,
        @Size(max = 160) String line2,
        @Size(max = 80) String city,
        @Size(max = 80) String state,
        @Size(min = 2, max = 2) String country,
        @Size(max = 20) String postalCode,
        @Size(max = 200) String reference,
        Boolean isDefault) {
}