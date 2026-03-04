package com.tecsup.app.micro.user.infrastructure.persistence.entity;

import com.tecsup.app.micro.user.domain.constant.ValidationConstants;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user_profiles")
public class UserProfileEntity {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(nullable = false, length = ValidationConstants.USERNAME_MAX)
    private String username;

    @Column(nullable = false, length = ValidationConstants.EMAIL_MAX)
    private String email;

    @Column(name = "full_name", nullable = false, length = ValidationConstants.FULL_NAME_MAX)
    private String fullName;

    @Column(length = ValidationConstants.PHONE_MAX)
    private String phone;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    void onCreate() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = Instant.now();
    }

    // NUEVO: Equals y HashCode basados en el ID del negocio
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof UserProfileEntity that))
            return false;
        return id != null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}