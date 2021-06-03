package com.commerce.backend.dao;

import com.commerce.backend.model.entity.Blog;

import com.commerce.backend.model.entity.BlogCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

    @Query(value = "SELECT b FROM Blog b WHERE CONCAT(b.title, b.description) LIKE %?1% and b.active = true order by created_at desc ",
            countQuery = "SELECT count(*) FROM Blog b WHERE b.active = true and CONCAT(b.title, b.description) LIKE %?1%",
            nativeQuery = false)
    Page<Blog> findAll(String keyword, Pageable pageable);

    @Query(value = "SELECT b FROM Blog b WHERE   b.active = true order by created_at desc ",
            countQuery = "SELECT count(*) FROM Blog b WHERE b.active = true",
            nativeQuery = false)
    Page<Blog> findAll(Pageable pageable);
    
    Page<Blog> findByCategory(BlogCategory category, Pageable pageable);
}
