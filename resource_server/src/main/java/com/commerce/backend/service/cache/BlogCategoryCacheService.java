package com.commerce.backend.service.cache;

import com.commerce.backend.dao.BlogCategoryRepository;
import com.commerce.backend.error.exception.ResourceNotFoundException;
import com.commerce.backend.model.dto.BlogCategoryDTO;
import com.commerce.backend.model.entity.BlogCategory;
import com.commerce.backend.model.request.blog.BlogCategoryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "blog_category")
public class BlogCategoryCacheService {

    @Autowired
    private final BlogCategoryRepository repo;


    public BlogCategoryCacheService(BlogCategoryRepository repo) {
        this.repo = repo;
    }

    @Cacheable(key = "#root.methodName")
    public List<BlogCategory> findAll()
    {
        return repo.findAll();
    }

    @Cacheable(key = "#id", unless = "{#root.caches[0].get(#id) == null, #result.equals(null)}")
    public Optional<BlogCategory> findById(Long id)
    {
        Optional<BlogCategory> category = repo.findById(id);
        if(!category.isPresent())
        {
            throw new ResourceNotFoundException("Category Not Found");
        }
        return category;
    }

    @Cacheable(key = "#name", unless = "{#root.caches[0].get(#name) == null, #result.equals(null)}")
    public Optional<BlogCategory> findByName(String name)
    {
        Optional<BlogCategory> category = repo.findByName(name);
        if(!category.isPresent())
        {
            throw new ResourceNotFoundException("Category Not Found");
        }
        return category;
    }

    @Cacheable(key = "#root.methodName")
    public BlogCategory createCategory(BlogCategoryRequest category)
    {
        BlogCategory cat = BlogCategory.builder()
                .name(category.getName())
                .description(category.getDescription())
                .created_at(new Date())
                .build();
        return repo.save(cat);
    }
}
