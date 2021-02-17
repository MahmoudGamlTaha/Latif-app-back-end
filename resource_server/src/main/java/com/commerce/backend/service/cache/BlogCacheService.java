package com.commerce.backend.service.cache;

import com.commerce.backend.model.entity.Blog;
import com.commerce.backend.model.response.blog.BlogResponse;

import java.util.List;

public interface BlogCacheService {
    List<Blog> findAll();
    Blog findById(Long id);
    List<Blog> getBlogByTitle(String title);
    Blog deleteBlog(Long id);;
}
