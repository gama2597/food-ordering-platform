package com.tecsup.app.micro.user.presentation.dto;

import com.tecsup.app.micro.user.domain.constant.ValidationConstants;
import jakarta.validation.constraints.*;

public record CreateAddressRequest(
                @NotBlank @Size(max = ValidationConstants.ADDRESS_LABEL_MAX) String label,
                @NotBlank @Size(max = ValidationConstants.ADDRESS_LINE_MAX) String line1,
                @Size(max = ValidationConstants.ADDRESS_LINE_MAX) String line2,
                @NotBlank @Size(max = ValidationConstants.ADDRESS_CITY_MAX) String city,
                @NotBlank @Size(max = ValidationConstants.ADDRESS_STATE_MAX) String state,
                @NotBlank @Size(min = ValidationConstants.ADDRESS_COUNTRY_MIN, max = ValidationConstants.ADDRESS_COUNTRY_MAX) String country,
                @Size(max = ValidationConstants.ADDRESS_POSTAL_MAX) String postalCode,
                @Size(max = ValidationConstants.ADDRESS_REF_MAX) String reference,
                Boolean isDefault) {
}