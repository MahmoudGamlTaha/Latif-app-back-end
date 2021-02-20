package com.commerce.backend.service;

import com.commerce.backend.model.dto.BlogDTO;
import com.commerce.backend.model.entity.Blog;
import com.commerce.backend.model.request.blog.BlogRequest;
import com.commerce.backend.model.request.blog.UpdateBlogRequest;
import com.commerce.backend.model.response.blog.BlogResponse;

import java.util.List;

public interface BlogService {
    List<BlogResponse> getBlogs();
    BlogResponse getBlogById(Long id);
    List<BlogResponse> search(String keyword);
    BlogResponse saveBlog(BlogRequest blog);
    String deleteBlog(Long id);
    BlogResponse update(UpdateBlogRequest blog);
}
