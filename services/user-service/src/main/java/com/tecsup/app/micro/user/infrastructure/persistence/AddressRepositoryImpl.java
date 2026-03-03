package com.tecsup.app.micro.user.infrastructure.persistence;

import com.tecsup.app.micro.user.domain.exception.UserNotFoundException;
import com.tecsup.app.micro.user.domain.model.Address;
import com.tecsup.app.micro.user.domain.port.AddressRepositoryPort;
import com.tecsup.app.micro.user.infrastructure.persistence.mapper.AddressEntityMapper;
import com.tecsup.app.micro.user.infrastructure.persistence.repository.JpaAddressRepository;
import com.tecsup.app.micro.user.infrastructure.persistence.repository.JpaUserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AddressRepositoryImpl implements AddressRepositoryPort {

    private final JpaAddressRepository addressJpa;
    private final JpaUserProfileRepository userJpa;
    private final AddressEntityMapper mapper;

    @Override
    public List<Address> findByUserId(UUID userId) {
        return addressJpa.findByUser_Id(userId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<Address> findByIdAndUserId(UUID addressId, UUID userId) {
        return addressJpa.findByIdAndUser_Id(addressId, userId).map(mapper::toDomain);
    }

    @Override
    public Address save(Address address) {
        var owner = userJpa.findById(address.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User profile not found: " + address.getUserId()));

        var entity = mapper.toEntity(address);
        entity.setUser(owner);

        var saved = addressJpa.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public void deleteByIdAndUserId(UUID addressId, UUID userId) {
        addressJpa.deleteByIdAndUserId(addressId, userId);
    }

    @Override
    public void clearDefault(UUID userId) {
        addressJpa.clearDefault(userId);
    }
}