package com.atssmart.api.repository;

import com.atssmart.api.enums.UserRole;
import com.atssmart.api.model.RoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByRole(UserRole role);
}
