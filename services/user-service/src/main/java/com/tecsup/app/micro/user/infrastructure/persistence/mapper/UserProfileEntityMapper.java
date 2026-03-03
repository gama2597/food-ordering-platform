package com.tecsup.app.micro.user.infrastructure.persistence.mapper;

import com.tecsup.app.micro.user.domain.model.UserProfile;
import com.tecsup.app.micro.user.infrastructure.persistence.entity.UserProfileEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserProfileEntityMapper {
    UserProfile toDomain(UserProfileEntity entity);

    UserProfileEntity toEntity(UserProfile domain);
}