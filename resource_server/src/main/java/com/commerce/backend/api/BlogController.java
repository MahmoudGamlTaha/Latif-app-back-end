package com.commerce.backend.api;

import com.commerce.backend.model.dto.BlogDTO;
import com.commerce.backend.model.entity.Blog;
import com.commerce.backend.model.request.blog.BlogRequest;
import com.commerce.backend.model.response.blog.BlogResponse;
import com.commerce.backend.service.BlogService;
import com.commerce.backend.service.BlogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public Blog getBlog(@PathVariable Long id)
    {
        return blogServiceImpl.getBlogById(id);
    }

    @GetMapping("/blogs/title={title}")
    public List<BlogResponse> getBlog(@PathVariable String title)
    {
        return blogServiceImpl.getBlogByTitle(title);
    }

    @PostMapping("/blogs/create")
    public Blog createBlog(@RequestBody @Valid BlogRequest blog)
    {
        return blogServiceImpl.saveBlog(blog);
    }

    @PostMapping("/blogs/delete")
    public ResponseEntity<BlogResponse> deleteBlog(Long id)
    {
        BlogResponse blog = blogServiceImpl.deleteBlog(id);
        return new ResponseEntity<>(blog, HttpStatus.OK);
    }
}
