package com.tecsup.app.micro.user.domain.port;

import com.tecsup.app.micro.user.domain.model.Address;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AddressRepositoryPort {
    List<Address> findByUserId(UUID userId);

    Optional<Address> findByIdAndUserId(UUID addressId, UUID userId);

    Address save(Address address);

    void deleteByIdAndUserId(UUID addressId, UUID userId);

    // regla: solo 1 default por user
    void clearDefault(UUID userId);
}