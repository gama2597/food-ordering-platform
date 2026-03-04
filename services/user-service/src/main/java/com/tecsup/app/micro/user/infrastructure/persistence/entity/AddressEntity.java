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
@Table(name = "addresses")
public class AddressEntity {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserProfileEntity user;

    @Column(nullable = false, length = ValidationConstants.ADDRESS_LABEL_MAX)
    private String label;

    @Column(nullable = false, length = ValidationConstants.ADDRESS_LINE_MAX)
    private String line1;

    @Column(length = ValidationConstants.ADDRESS_LINE_MAX)
    private String line2;

    @Column(nullable = false, length = ValidationConstants.ADDRESS_CITY_MAX)
    private String city;

    @Column(nullable = false, length = ValidationConstants.ADDRESS_STATE_MAX)
    private String state;

    @Column(nullable = false, length = ValidationConstants.ADDRESS_COUNTRY_MAX)
    private String country;

    @Column(name = "postal_code", length = ValidationConstants.ADDRESS_POSTAL_MAX)
    private String postalCode;

    @Column(length = ValidationConstants.ADDRESS_REF_MAX)
    private String reference;

    @Column(name = "is_default", nullable = false)
    private boolean isDefault;

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
        if (!(o instanceof AddressEntity that))
            return false;
        return id != null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}