package com.commerce.backend.service;

import com.commerce.backend.dao.BlogCategoryRepository;
import com.commerce.backend.model.dto.BlogCategoryDTO;
import com.commerce.backend.model.entity.BlogCategory;
import com.commerce.backend.model.request.blog.BlogCategoryRequest;
import com.commerce.backend.service.cache.BlogCategoryCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogCategoryServiceImpl {

    @Autowired
    private final BlogCategoryRepository repo;
    private final BlogCategoryCacheService cacheService;

    public BlogCategoryServiceImpl(BlogCategoryRepository repo, BlogCategoryCacheService cacheService) {
        this.repo = repo;
        this.cacheService = cacheService;
    }

    public List<BlogCategory> findAll()
    {
        return this.cacheService.findAll();
    }

    public Optional<BlogCategory> findById(Long id)
    {
        return cacheService.findById(id);
    }

    public Optional<BlogCategory> findByName(String name)
    {
        return cacheService.findByName(name);
    }

    public BlogCategory createCategory(BlogCategoryRequest blog)
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
