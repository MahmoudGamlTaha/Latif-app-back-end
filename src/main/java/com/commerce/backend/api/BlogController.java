package com.commerce.backend.api;

import com.commerce.backend.constants.SystemConstant;
import com.commerce.backend.model.request.blog.BlogRequest;
import com.commerce.backend.model.request.blog.UpdateBlogRequest;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.model.response.blog.BlogResponse;
import com.commerce.backend.service.BlogService;
import com.commerce.backend.service.BlogServiceImpl;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
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
    public ResponseEntity<BasicResponse> getBlogs(@RequestParam(required = false) Optional<Integer> page){
    	BasicResponse response = blogServiceImpl.getBlogs(page.orElse(0)); 
        return new ResponseEntity<BasicResponse>(response, HttpStatus.OK);
    }

    @GetMapping("/blogs/id={id}")
    public ResponseEntity<BasicResponse> getBlogById(@PathVariable Long id)
    {
        BasicResponse response = blogServiceImpl.getBlogById(id);
        return new ResponseEntity<BasicResponse>(response, HttpStatus.OK);
    }

    @GetMapping({"/blogs/keyword={keyword}/page={page}", "/blogs/keyword={keyword}"})
    public BasicResponse search(@PathVariable String keyword, @PathVariable(required = false) Integer page)
    { 
    	page = page == null?0:page;
        Pageable pageable = PageRequest.of(page, SystemConstant.MOBILE_PAGE_SIZE);
        return blogServiceImpl.search(keyword, pageable);
    }

    @PostMapping("/blogs/create")
    //@Timed
    public ResponseEntity<BasicResponse> createBlog(@RequestBody @Valid BlogRequest blog) {
        return new ResponseEntity<BasicResponse> (blogServiceImpl.saveBlog(blog,  blog.getExtrnImage(), blog.getImages(), blog.is_external()), HttpStatus.OK);
    }

    @PostMapping("/blogs/update")
    public ResponseEntity<BasicResponse> updateBlog(@RequestBody @Valid UpdateBlogRequest blogRequest,
    		                        @RequestParam(value = "external", required = false, defaultValue = "true") Boolean external,
                                    @RequestParam(value = "images", required = false) ArrayList<MultipartFile> images,
                                    @RequestParam(value = "images", required = false) ArrayList<String> externImages ) throws IOException {
    	
    	BasicResponse response = blogServiceImpl.update(blogRequest, external, images, externImages);
    	HttpStatus status = response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
    	return new ResponseEntity<BasicResponse>(response, status );
    }

    @PostMapping("/blogs/delete")
    public ResponseEntity<BasicResponse> deleteBlog(Long id)
    {
    	BasicResponse response  = blogServiceImpl.deleteBlog(id);
    	HttpStatus status = response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<BasicResponse>( response, status);
    }

    @PostMapping("/blogs/blog-by-category/id={category}")
    public ResponseEntity<BasicResponse> blogByCategory(@PathVariable Long category, @PathVariable(required = false) Optional<Integer> page, @PathVariable(required = false) Optional<Integer> size ){
    	Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(SystemConstant.MOBILE_PAGE_SIZE));
    	BasicResponse response  = blogServiceImpl.findBlogByCategory(category, pageable);
    	HttpStatus status = response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<BasicResponse>( response, status);
    }

    @PostMapping("/blogs/activate")
    public ResponseEntity<BasicResponse> activateBlog(@RequestParam(value = "id") Long id, @RequestParam(value = "active") boolean active)
    {
        BasicResponse response  = blogServiceImpl.activateBlog(id, active);
        HttpStatus status = response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<BasicResponse>( response, status);
    }
}
