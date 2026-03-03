package com.tecsup.app.micro.user.presentation.dto;

import jakarta.validation.constraints.*;

public record CreateAddressRequest(
        @NotBlank @Size(max = 60) String label,
        @NotBlank @Size(max = 160) String line1,
        @Size(max = 160) String line2,
        @NotBlank @Size(max = 80) String city,
        @NotBlank @Size(max = 80) String state,
        @NotBlank @Size(min = 2, max = 2) String country,
        @Size(max = 20) String postalCode,
        @Size(max = 200) String reference,
        Boolean isDefault) {
}