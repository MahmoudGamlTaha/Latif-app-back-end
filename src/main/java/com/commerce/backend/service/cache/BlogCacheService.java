package com.commerce.backend.service.cache;

import com.commerce.backend.model.entity.Blog;
import com.commerce.backend.model.request.blog.BlogRequest;
import com.commerce.backend.model.request.blog.UpdateBlogRequest;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.model.response.blog.BlogResponse;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BlogCacheService {
    Page<Blog> findAll(Integer  page);
    BlogResponse findById(Long id);
    List<Blog> search(String keyword);
    BasicResponse saveBlog(BlogRequest blog, List<String> externalPath, boolean external, List<MultipartFile> files);
    BlogResponse update(UpdateBlogRequest blog, MultipartFile file) throws IOException;
    BasicResponse deleteBlog(Long id);;
}
