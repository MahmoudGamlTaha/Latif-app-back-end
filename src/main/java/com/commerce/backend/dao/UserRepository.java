package com.commerce.backend.dao;

import com.commerce.backend.model.entity.Role;
import com.commerce.backend.model.entity.User;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
    Boolean existsByEmail(String email);
    User findByMobile(String mobile);
    List<User> findUsersByRoles(Role roles);
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value="Update users SET active = :active where id = :user_id", nativeQuery = true)
    Integer activateUser(Long user_id, boolean active);
}
