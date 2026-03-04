package com.tecsup.app.micro.user.presentation.dto;

import com.tecsup.app.micro.user.domain.constant.ValidationConstants;
import jakarta.validation.constraints.Size;

public record UpdateAddressRequest(
                @Size(max = ValidationConstants.ADDRESS_LABEL_MAX) String label,
                @Size(max = ValidationConstants.ADDRESS_LINE_MAX) String line1,
                @Size(max = ValidationConstants.ADDRESS_LINE_MAX) String line2,
                @Size(max = ValidationConstants.ADDRESS_CITY_MAX) String city,
                @Size(max = ValidationConstants.ADDRESS_STATE_MAX) String state,
                @Size(min = ValidationConstants.ADDRESS_COUNTRY_MIN, max = ValidationConstants.ADDRESS_COUNTRY_MAX) String country,
                @Size(max = ValidationConstants.ADDRESS_POSTAL_MAX) String postalCode,
                @Size(max = ValidationConstants.ADDRESS_REF_MAX) String reference,
                Boolean isDefault) {
}