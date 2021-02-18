package com.commerce.backend.service;

import com.commerce.backend.converter.blog.BlogCategoryResponseConverter;
import com.commerce.backend.dao.BlogCategoryRepository;
import com.commerce.backend.model.entity.BlogCategory;
import com.commerce.backend.model.request.blog.BlogCategoryRequest;
import com.commerce.backend.model.response.blog.BlogCategoryResponse;
import com.commerce.backend.service.cache.BlogCategoryCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BlogCategoryServiceImpl {

    @Autowired
    private final BlogCategoryRepository repo;
    private final BlogCategoryCacheService cacheService;
    private final BlogCategoryResponseConverter blogCategoryResponseConverter;

    public BlogCategoryServiceImpl(BlogCategoryRepository repo, BlogCategoryCacheService cacheService, BlogCategoryResponseConverter blogCategoryResponseConverter) {
        this.repo = repo;
        this.cacheService = cacheService;
        this.blogCategoryResponseConverter = blogCategoryResponseConverter;
    }

    public List<BlogCategoryResponse> findAll()
    {
        List<BlogCategory> cat = cacheService.findAll();
        return cat.
                stream()
                .map(blogCategoryResponseConverter)
                .collect(Collectors.toList());
    }

    public BlogCategoryResponse findById(Long id)
    {
        return cacheService.findById(id);
    }

    public BlogCategoryResponse findByName(String name)
    {
        return cacheService.findByName(name);
    }

    public BlogCategoryResponse createCategory(BlogCategoryRequest blog)
    {
        return cacheService.createCategory(blog);
    }
    public String deleteById(Long id)
    {
        Optional<BlogCategory> category = this.repo.findById(id);
        if(!category.isPresent())
        {
            return "Not Found";
        }
        this.repo.deleteById(id);
        return "Category Removed";
    }
}
