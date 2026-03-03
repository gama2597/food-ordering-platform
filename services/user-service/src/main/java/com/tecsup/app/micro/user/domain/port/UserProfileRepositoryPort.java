package com.tecsup.app.micro.user.domain.port;

import com.tecsup.app.micro.user.domain.model.UserProfile;
import java.util.Optional;
import java.util.UUID;

public interface UserProfileRepositoryPort {
    Optional<UserProfile> findById(UUID id);

    UserProfile save(UserProfile profile);
}