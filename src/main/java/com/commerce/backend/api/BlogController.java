package com.commerce.backend.api;

import com.commerce.backend.model.request.blog.BlogRequest;
import com.commerce.backend.model.request.blog.UpdateBlogRequest;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.model.response.blog.BlogResponse;
import com.commerce.backend.service.BlogService;
import com.commerce.backend.service.BlogServiceImpl;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController
public class BlogController extends PublicApiController{

    private final BlogService blogServiceImpl;

    @Autowired
    public BlogController(BlogServiceImpl blogServiceImpl)
    {
        this.blogServiceImpl = blogServiceImpl;
    }

    @GetMapping("/blogs")
    public ResponseEntity<BasicResponse> getBlogs(@RequestParam(required = false) Optional<Integer> page)
    {
    	BasicResponse response = blogServiceImpl.getBlogs(page.orElse(0)); 
        return new ResponseEntity<BasicResponse>(response, HttpStatus.OK);
    }

    @GetMapping("/blogs/id={id}")
    public ResponseEntity<BasicResponse> getBlogById(@PathVariable Long id)
    {
        BasicResponse response = blogServiceImpl.getBlogById(id);
        return new ResponseEntity<BasicResponse>(response, HttpStatus.OK);
    }

    @GetMapping("/blogs/keyword={keyword}")
    public BasicResponse search(@PathVariable String keyword, Pageable pageable)
    {
        return blogServiceImpl.search(keyword, pageable);
    }

    @PostMapping("/blogs/create")
    @Timed
    public ResponseEntity<BasicResponse> createBlog(@ModelAttribute @Valid BlogRequest blog,
                                   @RequestParam(value = "files", required = false)
                                   Optional<List<MultipartFile>> file,
                                   @RequestParam(value = "extrnFile", required = false)
                                   Optional<List<String>> extrnFile,
                                   boolean isExternal) {
        return new ResponseEntity<BasicResponse> (blogServiceImpl.saveBlog(blog,  extrnFile.orElse(null),file.orElse(null),isExternal), HttpStatus.OK);
    }

    @PostMapping("/update")
    public BasicResponse updateBlog(@ModelAttribute @Valid UpdateBlogRequest blogRequest,
                                   @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        return blogServiceImpl.update(blogRequest, file);
    }

    @PostMapping("/blogs/delete")
    public BasicResponse deleteBlog(Long id)
    {
        return blogServiceImpl.deleteBlog(id);
    }
}
