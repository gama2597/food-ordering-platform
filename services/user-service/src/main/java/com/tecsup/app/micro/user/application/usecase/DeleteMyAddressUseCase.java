package com.tecsup.app.micro.user.application.usecase;

import java.util.UUID;

public interface DeleteMyAddressUseCase {
    void deleteMyAddress(String subject, UUID addressId);
}