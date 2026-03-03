package com.tecsup.app.micro.user.infrastructure.persistence.mapper;

import com.tecsup.app.micro.user.domain.model.Address;
import com.tecsup.app.micro.user.infrastructure.persistence.entity.AddressEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AddressEntityMapper {

    @Mapping(source = "user.id", target = "userId")
    Address toDomain(AddressEntity entity);

    // user se setea en el repo impl (owner), por eso se ignora aquí
    @Mapping(target = "user", ignore = true)
    AddressEntity toEntity(Address domain);
}