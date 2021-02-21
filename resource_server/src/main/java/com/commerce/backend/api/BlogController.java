package com.commerce.backend.api;

import com.commerce.backend.model.request.blog.BlogRequest;
import com.commerce.backend.model.response.blog.BlogResponse;
import com.commerce.backend.service.BlogService;
import com.commerce.backend.service.BlogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping("/blogs/title={title}")
    public List<BlogResponse> getBlogByTitle(@PathVariable String title)
    {
        return blogServiceImpl.getBlogByTitle(title);
    }

    @PostMapping("/blogs/create")
    public BlogResponse createBlog(@RequestBody @Valid BlogRequest blog)
    {
        return blogServiceImpl.saveBlog(blog);
    }

    @PostMapping("/blogs/delete")
    public String deleteBlog(Long id)
    {
        return blogServiceImpl.deleteBlog(id);
    }
}
