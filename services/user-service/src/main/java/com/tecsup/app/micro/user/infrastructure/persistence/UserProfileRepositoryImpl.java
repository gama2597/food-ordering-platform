package com.tecsup.app.micro.user.infrastructure.persistence;

import com.tecsup.app.micro.user.domain.model.UserProfile;
import com.tecsup.app.micro.user.domain.port.UserProfileRepositoryPort;
import com.tecsup.app.micro.user.infrastructure.persistence.mapper.UserProfileEntityMapper;
import com.tecsup.app.micro.user.infrastructure.persistence.repository.JpaUserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserProfileRepositoryImpl implements UserProfileRepositoryPort {

    private final JpaUserProfileRepository jpa;
    private final UserProfileEntityMapper mapper;

    @Override
    public Optional<UserProfile> findById(UUID id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public UserProfile save(UserProfile profile) {
        var saved = jpa.save(mapper.toEntity(profile));
        return mapper.toDomain(saved);
    }
}