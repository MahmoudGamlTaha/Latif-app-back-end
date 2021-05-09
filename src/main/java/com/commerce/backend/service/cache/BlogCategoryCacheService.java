package com.commerce.backend.service.cache;

import com.commerce.backend.dao.BlogCategoryRepository;
import com.commerce.backend.error.exception.ResourceNotFoundException;
import com.commerce.backend.model.entity.BlogCategory;
import com.commerce.backend.model.request.blog.BlogCategoryRequest;
import com.commerce.backend.model.request.blog.UpdateBlogCategoryRequest;
import com.commerce.backend.model.response.blog.BlogCategoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<BlogCategory> findAll(Pageable page)
    {
        return repo.findByActive(true, page);//(page);
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
    public BlogCategoryResponse createCategory(BlogCategoryRequest category)
    {
        BlogCategory cat = BlogCategory.builder()
                .name(category.getName())
                .nameAr(category.getNameAr())
                .description(category.getDescription())
                .externalLink(category.getExternal_link())
                .active(category.isActive())
                .icon(category.getIcon())
                .iconSelect(category.getIcon_select())
                .created_at(new Date())
                .build();
        BlogCategory category1 = repo.save(cat);
        return new BlogCategoryResponse(category1);
    }
}
