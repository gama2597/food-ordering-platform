package com.tecsup.app.micro.user.application.usecase;

import com.tecsup.app.micro.user.domain.model.Address;

public interface CreateMyAddressUseCase {
    Address createMyAddress(String subject, CreateAddressCommand cmd);

    record CreateAddressCommand(
            String label, String line1, String line2, String city, String state, String country,
            String postalCode, String reference, boolean isDefault) {
    }
}