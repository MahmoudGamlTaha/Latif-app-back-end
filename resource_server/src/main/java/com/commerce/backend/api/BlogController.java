package com.commerce.backend.api;

import com.commerce.backend.model.entity.Blog;
import com.commerce.backend.model.request.blog.BlogRequest;
import com.commerce.backend.model.request.blog.UpdateBlogRequest;
import com.commerce.backend.model.response.blog.BlogResponse;
import com.commerce.backend.service.BlogService;
import com.commerce.backend.service.BlogServiceImpl;
import io.swagger.models.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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

    @GetMapping("/blogs/keyword={keyword}")
    public List<BlogResponse> search(@PathVariable String keyword)
    {
        return blogServiceImpl.search(keyword);
    }

    @PostMapping("/blogs/create")
    public BlogResponse createBlog(@RequestBody @Valid BlogRequest blog)
    {
        return blogServiceImpl.saveBlog(blog);
    }

    @PostMapping("/update")
    public BlogResponse updateBlog(@RequestBody @Valid UpdateBlogRequest blogRequest)
    {
        return blogServiceImpl.update(blogRequest);
    }

    @PostMapping("/blogs/delete")
    public String deleteBlog(Long id)
    {
        return blogServiceImpl.deleteBlog(id);
    }
}
