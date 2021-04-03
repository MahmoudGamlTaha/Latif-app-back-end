package com.commerce.backend.dao;

import com.commerce.backend.model.entity.Blog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

    @Query(value = "SELECT b FROM Blogs b WHERE CONCAT(b.title, b.description) LIKE %?1%",
            countQuery = "SELECT count(*) FROM Blog b WHERE CONCAT(b.title, b.description) LIKE %?1%",
            nativeQuery = true)
    Page<Blog> findAll(String keyword, Pageable pageable);
}
