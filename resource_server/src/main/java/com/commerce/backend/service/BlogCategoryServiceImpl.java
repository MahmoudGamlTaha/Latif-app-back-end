package com.commerce.backend.service;

import com.commerce.backend.converter.blog.BlogCategoryResponseConverter;
import com.commerce.backend.dao.BlogCategoryRepository;
import com.commerce.backend.model.entity.Blog;
import com.commerce.backend.model.entity.BlogCategory;
import com.commerce.backend.model.event.updateCategoryRequest;
import com.commerce.backend.model.request.blog.BlogCategoryRequest;
import com.commerce.backend.model.request.blog.UpdateBlogRequest;
import com.commerce.backend.model.response.blog.BlogCategoryResponse;
import com.commerce.backend.model.response.blog.BlogResponse;
import com.commerce.backend.service.cache.BlogCategoryCacheService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
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

    public List<BlogCategoryResponse> search(String keyword)
    {
        List<BlogCategory> cat = cacheService.search(keyword);
        return cat.
                stream()
                .map(blogCategoryResponseConverter)
                .collect(Collectors.toList());
    }

    public BlogCategoryResponse update(updateCategoryRequest CatRequest)
    {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        SessionFactory factory = configuration.buildSessionFactory();
        Session session = factory.openSession();

        Transaction tx = session.beginTransaction();
        BlogCategory cat = session.load(BlogCategory.class, CatRequest.getId());

        cat.setName(CatRequest.getName());
        if(CatRequest.getDescription() != null) {
            cat.setDescription(CatRequest.getDescription());
        }
        session.update(cat);
        tx.commit();
        session.close();

        return new BlogCategoryResponse(cat);


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
