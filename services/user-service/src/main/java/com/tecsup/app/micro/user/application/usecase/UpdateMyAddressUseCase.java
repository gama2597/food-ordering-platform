package com.tecsup.app.micro.user.application.usecase;

import com.tecsup.app.micro.user.domain.model.Address;
import java.util.UUID;

public interface UpdateMyAddressUseCase {
    Address updateMyAddress(String subject, UUID addressId, UpdateAddressCommand cmd);

    record UpdateAddressCommand(
            String label, String line1, String line2, String city, String state, String country,
            String postalCode, String reference, Boolean isDefault) {
    }
}