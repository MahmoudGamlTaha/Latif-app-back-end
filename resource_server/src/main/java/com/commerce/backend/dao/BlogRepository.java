package com.commerce.backend.dao;

import com.commerce.backend.model.entity.Blog;
import com.commerce.backend.model.response.blog.BlogResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

    List<Blog> getBlogByTitle(String title);
}
