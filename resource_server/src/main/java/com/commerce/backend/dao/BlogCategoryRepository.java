package com.commerce.backend.dao;

import com.commerce.backend.model.entity.BlogCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlogCategoryRepository extends JpaRepository<BlogCategory, Long> {
    @Query("SELECT b FROM BlogCategory b WHERE CONCAT(b.name, b.description) LIKE %?1%")
    List<BlogCategory> findAll(String keyword);

    Optional<BlogCategory> findByName(String name);
}
