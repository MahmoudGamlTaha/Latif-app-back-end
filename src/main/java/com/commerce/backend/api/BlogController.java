package com.commerce.backend.api;

import com.commerce.backend.model.request.blog.BlogRequest;
import com.commerce.backend.model.request.blog.UpdateBlogRequest;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.model.response.blog.BlogResponse;
import com.commerce.backend.service.BlogService;
import com.commerce.backend.service.BlogServiceImpl;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
public class BlogController extends PublicApiController{

    private final BlogService blogServiceImpl;


    @Autowired
    public BlogController(BlogServiceImpl blogServiceImpl)
    {
        this.blogServiceImpl = blogServiceImpl;
    }

    @GetMapping("/blogs")
    public List<BlogResponse> getBlogs()
    {
        return blogServiceImpl.getBlogs();
    }

    @GetMapping("/blogs/id={id}")
    public BlogResponse getBlogById(@PathVariable Long id)
    {
        return blogServiceImpl.getBlogById(id);
    }

    @GetMapping("/blogs/keyword={keyword}")
    public List<BlogResponse> search(@PathVariable String keyword)
    {
        return blogServiceImpl.search(keyword);
    }

    @PostMapping("/blogs/create")
    @Timed
    public BlogResponse createBlog(@ModelAttribute @Valid BlogRequest blog,
                                   @RequestParam(value = "image", required = false) MultipartFile file) {
        return blogServiceImpl.saveBlog(blog, file);
    }

    @PostMapping("/update")
    public BlogResponse updateBlog(@ModelAttribute @Valid UpdateBlogRequest blogRequest,
                                   @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        return blogServiceImpl.update(blogRequest, file);
    }

    @PostMapping("/blogs/delete")
    public BasicResponse deleteBlog(Long id)
    {
        return blogServiceImpl.deleteBlog(id);
    }
}
