package com.commerce.backend.service;


import com.commerce.backend.model.request.blog.BlogRequest;
import com.commerce.backend.model.request.blog.UpdateBlogRequest;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.model.response.blog.BlogResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BlogService {
    BasicResponse getBlogs(Integer page);
    BasicResponse getBlogById(Long id);
    
    BasicResponse search(String keyword);
    BlogResponse saveBlog(BlogRequest blog, MultipartFile file);
    BasicResponse deleteBlog(Long id);
    BlogResponse update(UpdateBlogRequest blog, MultipartFile file) throws IOException;
}
