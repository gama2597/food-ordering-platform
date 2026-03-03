package com.tecsup.app.micro.user.application.service.impl;

import com.tecsup.app.micro.user.application.service.UserApplicationService;
import com.tecsup.app.micro.user.application.usecase.CreateMyAddressUseCase.CreateAddressCommand;
import com.tecsup.app.micro.user.application.usecase.GetMyProfileUseCase.MyPrincipal;
import com.tecsup.app.micro.user.application.usecase.GetMyProfileUseCase.UserProfileResult;
import com.tecsup.app.micro.user.application.usecase.UpdateMyAddressUseCase.UpdateAddressCommand;
import com.tecsup.app.micro.user.domain.exception.AddressNotFoundException;
import com.tecsup.app.micro.user.domain.exception.UserNotFoundException;
import com.tecsup.app.micro.user.domain.model.Address;
import com.tecsup.app.micro.user.domain.model.UserProfile;
import com.tecsup.app.micro.user.domain.port.AddressRepositoryPort;
import com.tecsup.app.micro.user.domain.port.UserProfileRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserApplicationServiceImpl implements UserApplicationService {

    private final UserProfileRepositoryPort userRepo;
    private final AddressRepositoryPort addressRepo;

    @Override
    public UserProfileResult getMyProfile(MyPrincipal principal) {
        UUID userId = safeUuid(principal.subject());

        var profile = userRepo.findById(userId).orElseGet(() -> {
            var fullName = (principal.fullName() != null && !principal.fullName().isBlank())
                    ? principal.fullName()
                    : principal.username();

            var created = UserProfile.provision(userId, principal.username(), principal.email(), fullName);
            return userRepo.save(created);
        });

        return new UserProfileResult(profile, principal.roles());
    }

    @Override
    public UserProfile updateMyProfile(String subject, String fullName, String phone) {
        UUID userId = safeUuid(subject);
        var profile = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Profile not found: " + userId));

        profile.updateProfile(fullName, phone);
        return userRepo.save(profile);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Address> listMyAddresses(String subject) {
        UUID userId = safeUuid(subject);
        return addressRepo.findByUserId(userId);
    }

    @Override
    public Address createMyAddress(String subject, CreateAddressCommand cmd) {
        UUID userId = safeUuid(subject);

        boolean willBeDefault = cmd.isDefault();
        if (willBeDefault)
            addressRepo.clearDefault(userId);

        var address = Address.create(
                userId,
                cmd.label(), cmd.line1(), cmd.line2(), cmd.city(), cmd.state(), cmd.country(),
                cmd.postalCode(), cmd.reference(),
                willBeDefault);

        // Pro opcional: si es su primera dirección y no pidió default, la hacemos
        // default
        if (!willBeDefault && addressRepo.findByUserId(userId).isEmpty()) {
            addressRepo.clearDefault(userId);
            address.update(null, null, address.getLine2(), null, null, null, address.getPostalCode(),
                    address.getReference(), true);
        }

        return addressRepo.save(address);
    }

    @Override
    public Address updateMyAddress(String subject, UUID addressId, UpdateAddressCommand cmd) {
        UUID userId = safeUuid(subject);

        var existing = addressRepo.findByIdAndUserId(addressId, userId)
                .orElseThrow(() -> new AddressNotFoundException("Address not found: " + addressId));

        if (Boolean.TRUE.equals(cmd.isDefault()))
            addressRepo.clearDefault(userId);

        existing.update(cmd.label(), cmd.line1(), cmd.line2(), cmd.city(), cmd.state(), cmd.country(),
                cmd.postalCode(), cmd.reference(), cmd.isDefault());

        return addressRepo.save(existing);
    }

    @Override
    public void deleteMyAddress(String subject, UUID addressId) {
        UUID userId = safeUuid(subject);
        addressRepo.deleteByIdAndUserId(addressId, userId);
    }

    private UUID safeUuid(String subject) {
        try {
            return UUID.fromString(subject);
        } catch (Exception ex) {
            return UUID.nameUUIDFromBytes(subject.getBytes(StandardCharsets.UTF_8));
        }
    }
}