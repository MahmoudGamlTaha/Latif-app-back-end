package com.commerce.backend.api;

import com.commerce.backend.model.dto.BlogCategoryDTO;
import com.commerce.backend.model.entity.BlogCategory;
import com.commerce.backend.model.request.blog.BlogCategoryRequest;
import com.commerce.backend.service.BlogCategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class blogCategoriesController extends PublicApiController{

    private final BlogCategoryServiceImpl blogCategoryService;

    @Autowired
    public blogCategoriesController(BlogCategoryServiceImpl blogCategoryService) {
        this.blogCategoryService = blogCategoryService;
    }

    @GetMapping("/blogCategory")
    public List<BlogCategory> getAll()
    {
        return blogCategoryService.findAll();
    }

    @GetMapping("/blogCategory/id={id}")
    public Optional<BlogCategory> getById(@PathVariable Long id)
    {
        return blogCategoryService.findById(id);
    }


    @GetMapping("/blogCategory/name={name}")
    public Optional<BlogCategory> getByName(@PathVariable String name)
    {
        return blogCategoryService.findByName(name);
    }

    @PostMapping("/blogCategory/create")
    public BlogCategory createCategory(@RequestBody @Valid BlogCategoryRequest category)
    {
        return blogCategoryService.createCategory(category);
    }

    @PostMapping("/blogCategory/delete")
    public ResponseEntity<String> deleteCategory(Long id)
    {
        String category = this.blogCategoryService.deleteById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

}
