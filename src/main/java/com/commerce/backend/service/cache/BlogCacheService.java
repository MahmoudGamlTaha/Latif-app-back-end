package com.commerce.backend.service.cache;

import com.commerce.backend.model.entity.Blog;
import com.commerce.backend.model.request.blog.BlogRequest;
import com.commerce.backend.model.request.blog.UpdateBlogRequest;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.model.response.blog.BlogResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BlogCacheService {
    Page<Blog> findAll(Integer  page);
    Blog findById(Long id);
    Page<Blog> search(String keyword, Pageable pageable);
    BasicResponse saveBlog(BlogRequest blog, List<String> externalPath, boolean external, List<MultipartFile> files);
    BlogResponse  update(UpdateBlogRequest blogRequest, boolean external, List<MultipartFile> images, List<String> externImage ) throws IOException;
    BasicResponse deleteBlog(Long id);;
}
