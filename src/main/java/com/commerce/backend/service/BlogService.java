package com.commerce.backend.service;


import com.commerce.backend.model.request.blog.BlogRequest;
import com.commerce.backend.model.request.blog.UpdateBlogRequest;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.model.response.blog.BlogResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BlogService {
    BasicResponse getBlogs(Integer page);
    BasicResponse getBlogById(Long id);
    
    BasicResponse search(String keyword, Pageable pageable);
    BasicResponse saveBlog(BlogRequest blog, List<String> externFiles, List<MultipartFile> files, boolean external);
    BasicResponse deleteBlog(Long id);
    BasicResponse update(UpdateBlogRequest blog, boolean external, List<MultipartFile> images, List<String> externImage ) throws IOException;
    BasicResponse findBlogByCategory(Long category, Pageable page);

    BasicResponse activateBlog(Long id, boolean active);
}
