package com.tecsup.app.micro.user.presentation.mapper;

import com.tecsup.app.micro.user.domain.model.Address;
import com.tecsup.app.micro.user.domain.model.UserProfile;
import com.tecsup.app.micro.user.presentation.dto.AddressResponse;
import com.tecsup.app.micro.user.presentation.dto.UserProfileResponse;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {

    @Mapping(target = "roles", expression = "java(roles)")
    UserProfileResponse toUserProfileResponse(UserProfile profile, List<String> roles);

    AddressResponse toAddressResponse(Address address);
}