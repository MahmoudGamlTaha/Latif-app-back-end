package com.commerce.backend.dao;

import com.commerce.backend.model.entity.Blog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

    @Query("SELECT b FROM Blog b WHERE CONCAT(b.title, b.description) LIKE %?1%")
    List<Blog> findAll(String keyword);
}
