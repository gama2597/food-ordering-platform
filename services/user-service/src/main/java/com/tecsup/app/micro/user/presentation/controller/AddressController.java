package com.tecsup.app.micro.user.presentation.controller;

import com.tecsup.app.micro.user.application.service.UserApplicationService;
import com.tecsup.app.micro.user.application.usecase.CreateMyAddressUseCase.CreateAddressCommand;
import com.tecsup.app.micro.user.application.usecase.UpdateMyAddressUseCase.UpdateAddressCommand;
import com.tecsup.app.micro.user.presentation.dto.*;
import com.tecsup.app.micro.user.presentation.mapper.UserDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users/me/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final UserApplicationService service;
    private final UserDtoMapper mapper;

    @GetMapping
    public AddressListResponse list(@AuthenticationPrincipal Jwt jwt) {
        var addresses = service.listMyAddresses(jwt.getSubject());
        // Usamos el nuevo método del mapper
        return new AddressListResponse(mapper.toAddressResponseList(addresses));
    }

    @PostMapping
    public ResponseEntity<AddressResponse> create(@AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody CreateAddressRequest req) {
        var created = service.createMyAddress(jwt.getSubject(),
                new CreateAddressCommand(
                        req.label(), req.line1(), req.line2(), req.city(), req.state(), req.country(),
                        req.postalCode(), req.reference(),
                        Boolean.TRUE.equals(req.isDefault())));

        return ResponseEntity
                .created(URI.create("/api/v1/users/me/addresses/" + created.getId()))
                .body(mapper.toAddressResponse(created));
    }

    @PutMapping("/{addressId}")
    public AddressResponse update(@AuthenticationPrincipal Jwt jwt, @PathVariable UUID addressId,
            @Valid @RequestBody UpdateAddressRequest req) {
        var updated = service.updateMyAddress(jwt.getSubject(), addressId,
                new UpdateAddressCommand(
                        req.label(), req.line1(), req.line2(), req.city(), req.state(), req.country(),
                        req.postalCode(), req.reference(), req.isDefault()));

        return mapper.toAddressResponse(updated);
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal Jwt jwt, @PathVariable UUID addressId) {
        service.deleteMyAddress(jwt.getSubject(), addressId);
        return ResponseEntity.noContent().build();
    }
}