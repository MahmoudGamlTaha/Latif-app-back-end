package com.commerce.backend.service;

import com.commerce.backend.converter.blog.BlogCategoryResponseConverter;
import com.commerce.backend.dao.BlogCategoryRepository;
import com.commerce.backend.error.exception.ResourceNotFoundException;
import com.commerce.backend.model.entity.BlogCategory;
import com.commerce.backend.model.request.blog.BlogCategoryRequest;
import com.commerce.backend.model.request.blog.UpdateCategoryRequest;
import com.commerce.backend.model.response.blog.BlogCategoryResponse;
import com.commerce.backend.service.cache.BlogCategoryCacheService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
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

    public List<BlogCategoryResponse> search(String keyword)
    {
        List<BlogCategory> cat = cacheService.search(keyword);
        return cat.
                stream()
                .map(blogCategoryResponseConverter)
                .collect(Collectors.toList());
    }

    public BlogCategoryResponse update(UpdateCategoryRequest CatRequest)
    {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        SessionFactory factory = configuration.buildSessionFactory();
        Session session = factory.openSession();

        Transaction tx = session.beginTransaction();
        if(CatRequest.getId() == null){
            throw new ResourceNotFoundException("Not Found");
        }
        BlogCategory cat = session.load(BlogCategory.class, CatRequest.getId());
        if(Objects.isNull(cat)){
            throw new ResourceNotFoundException("Not Found");
        }
        if(CatRequest.getName() != null) {
            cat.setName(CatRequest.getName());
        }
        if(CatRequest.getDescription() != null) {
            cat.setDescription(CatRequest.getDescription());
        }
        session.update(cat);
        tx.commit();
        session.close();

        return new BlogCategoryResponse(cat);


    }

    public BlogCategoryResponse createCategory(@Valid BlogCategoryRequest blog)
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
