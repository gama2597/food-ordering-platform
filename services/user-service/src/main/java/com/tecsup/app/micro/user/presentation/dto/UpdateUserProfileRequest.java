package com.tecsup.app.micro.user.presentation.dto;

import com.tecsup.app.micro.user.domain.constant.ValidationConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUserProfileRequest(
                @NotBlank @Size(min = ValidationConstants.FULL_NAME_MIN, max = ValidationConstants.FULL_NAME_MAX) String fullName,
                @Size(max = ValidationConstants.PHONE_MAX) String phone) {
}