package com.tecsup.app.micro.user.infrastructure.persistence.repository;

import com.tecsup.app.micro.user.infrastructure.persistence.entity.AddressEntity;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaAddressRepository extends JpaRepository<AddressEntity, UUID> {

    List<AddressEntity> findByUser_Id(UUID userId);

    Optional<AddressEntity> findByIdAndUser_Id(UUID id, UUID userId);

    @Modifying
    @Transactional
    @Query("update AddressEntity a set a.isDefault = false where a.user.id = :userId and a.isDefault = true")
    int clearDefault(@Param("userId") UUID userId);

    @Modifying
    @Transactional
    @Query("delete from AddressEntity a where a.id = :addressId and a.user.id = :userId")
    int deleteByIdAndUserId(@Param("addressId") UUID addressId, @Param("userId") UUID userId);
}