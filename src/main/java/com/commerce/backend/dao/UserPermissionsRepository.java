package com.commerce.backend.dao;

import com.commerce.backend.model.entity.UserPermissions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPermissionsRepository extends JpaRepository<UserPermissions, Long> {
    Page<UserPermissions> findAll(Pageable pageable);
    boolean existsUserPermissionsByHttpPath(String httpPath);
}
