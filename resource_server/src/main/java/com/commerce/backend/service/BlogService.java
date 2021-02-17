package com.commerce.backend.service;

import com.commerce.backend.model.dto.BlogDTO;
import com.commerce.backend.model.entity.Blog;
import com.commerce.backend.model.request.blog.BlogRequest;
import com.commerce.backend.model.response.blog.BlogResponse;

import java.util.List;

public interface BlogService {
    List<BlogResponse> getBlogs();
    Blog getBlogById(Long id);
    List<BlogResponse> getBlogByTitle(String title);
    Blog saveBlog(BlogRequest blog);
    BlogResponse deleteBlog(Long id);
}
