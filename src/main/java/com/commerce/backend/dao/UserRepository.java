package com.commerce.backend.dao;

import com.commerce.backend.model.entity.Role;
import com.commerce.backend.model.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
    Boolean existsByEmail(String email);
    User findByMobile(String mobile);
    List<User> findUsersByRoles(Role roles);
}
