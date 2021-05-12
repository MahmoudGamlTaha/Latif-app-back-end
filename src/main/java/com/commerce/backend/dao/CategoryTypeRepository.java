package com.commerce.backend.dao;

import com.commerce.backend.model.entity.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryTypeRepository extends JpaRepository<CategoryType, Long> {
    @Query("SELECT b FROM CategoryType b WHERE b.active = true")
    List<CategoryType> findAll(String keyword);
    Optional<CategoryType> findByName(String name);
}
