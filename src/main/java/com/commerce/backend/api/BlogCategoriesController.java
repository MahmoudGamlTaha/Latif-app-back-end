package com.commerce.backend.api;

import com.commerce.backend.constants.SystemConstant;
import com.commerce.backend.model.request.blog.BlogCategoryRequest;
import com.commerce.backend.model.request.blog.UpdateBlogCategoryRequest;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.model.response.blog.BlogCategoryResponse;
import com.commerce.backend.service.BlogCategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class BlogCategoriesController extends PublicApiController{

    private final BlogCategoryServiceImpl blogCategoryService;

    @Autowired
    public BlogCategoriesController(BlogCategoryServiceImpl blogCategoryService) {
        this.blogCategoryService = blogCategoryService;
    }

    @GetMapping("/blogCategory")
    public BasicResponse getAll(@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size ){
     Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(SystemConstant.MOBILE_PAGE_SIZE));
    	return blogCategoryService.findAll(pageable);
    }

    @GetMapping("/blogCategory/id={id}")
    public BlogCategoryResponse getById(@PathVariable Long id){
        return blogCategoryService.findById(id);
    }


    @GetMapping("/blogCategory/keyword={keyword}")
    public List<BlogCategoryResponse> search(@PathVariable String keyword)
    {
        return blogCategoryService.search(keyword);
    }

    @PostMapping("/blogCategory/create")
    public BasicResponse createCategory(@RequestBody @Valid BlogCategoryRequest category)
    {
        return blogCategoryService.createCategory(category);
    }

    @PostMapping("/blogCategory/update")
    public BlogCategoryResponse updateCategory(@RequestBody @Valid UpdateBlogCategoryRequest catRequest)
    {
        return blogCategoryService.update(catRequest);
    }

    @PostMapping("/blogCategory/delete")
    public ResponseEntity<BasicResponse> deleteCategory(Long id)
    {
        BasicResponse category = this.blogCategoryService.deleteById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

}
