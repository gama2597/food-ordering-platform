package com.tecsup.app.micro.user.infrastructure.persistence.mapper;

import com.tecsup.app.micro.user.domain.model.Address;
import com.tecsup.app.micro.user.infrastructure.persistence.entity.AddressEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AddressEntityMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(target = "isDefault", expression = "java(entity.isDefault())")
    Address toDomain(AddressEntity entity);

    @Mapping(target = "user", ignore = true)
    AddressEntity toEntity(Address domain);

    // ✅ Fuerza el set del flag al Entity (porque Lombok suele generar setDefault()
    // y MapStruct se confunde)
    @AfterMapping
    default void afterToEntity(Address domain, @MappingTarget AddressEntity entity) {
        entity.setDefault(domain.isDefault());
    }
}