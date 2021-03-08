package com.commerce.backend.service.cache;

import com.commerce.backend.dao.BlogCategoryRepository;
import com.commerce.backend.error.exception.ResourceNotFoundException;
import com.commerce.backend.model.entity.BlogCategory;
import com.commerce.backend.model.request.blog.updateCategoryRequest;
import com.commerce.backend.model.response.blog.BlogCategoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

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
    public BlogCategoryResponse findById(Long id)
    {
        BlogCategory category = repo.findById(id).orElse(null);
        if(Objects.isNull(category))
        {
            throw new ResourceNotFoundException("Category Not Found");
        }
        return new BlogCategoryResponse(category);
    }

    @Cacheable(key = "#keyword", unless = "{#root.caches[0].get(#keyword) == null, #result.equals(null)}")
    public List<BlogCategory>  search(String keyword)
    {
        return repo.findAll(keyword);
    }

    @Cacheable(key = "#root.methodName")
    public BlogCategoryResponse createCategory(updateCategoryRequest category)
    {
        BlogCategory cat = BlogCategory.builder()
                .name(category.getName())
                .description(category.getDescription())
                .created_at(new Date())
                .build();
        BlogCategory category1 = repo.save(cat);
        return new BlogCategoryResponse(category1);
    }
}
