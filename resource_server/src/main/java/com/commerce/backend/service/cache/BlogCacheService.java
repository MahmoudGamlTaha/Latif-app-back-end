package com.commerce.backend.service.cache;

import com.commerce.backend.model.entity.Blog;
import com.commerce.backend.model.request.blog.BlogRequest;
import com.commerce.backend.model.request.blog.UpdateBlogRequest;
import com.commerce.backend.model.response.blog.BlogResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BlogCacheService {
    List<Blog> findAll();
    BlogResponse findById(Long id);
    List<Blog> search(String keyword);
    BlogResponse saveBlog(BlogRequest blog, MultipartFile file);
    BlogResponse update(UpdateBlogRequest blog, MultipartFile file) throws IOException;
    String deleteBlog(Long id);;
}
