package com.atssmart.api.repository;

import com.atssmart.api.enums.UserRole;
import com.atssmart.api.model.RoleEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByRoleEnum(UserRole roleEnum);
}
