package com.commerce.backend.service;

import com.commerce.backend.constants.MessageType;
import com.commerce.backend.converter.blog.BlogCategoryResponseConverter;
import com.commerce.backend.dao.BlogCategoryRepository;
import com.commerce.backend.error.exception.ResourceNotFoundException;
import com.commerce.backend.model.entity.BlogCategory;
import com.commerce.backend.model.request.blog.BlogCategoryRequest;
import com.commerce.backend.model.request.blog.UpdateBlogCategoryRequest;
import com.commerce.backend.model.request.blog.UpdateBlogRequest;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.model.response.blog.BlogCategoryResponse;
import com.commerce.backend.service.cache.BlogCategoryCacheService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

import java.util.HashMap;
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

    public BasicResponse findAll(Pageable page)
    {
    	 BasicResponse response = new BasicResponse();
    	 HashMap<String, Object> keyResponse = new HashMap<String, Object>();
    	try {
        Page<BlogCategory> cat = cacheService.findAll(page);
        List<BlogCategoryResponse> catList  = cat.
                get()
                .map(blogCategoryResponseConverter)
                .collect(Collectors.toList());
       
        response.setMsg(MessageType.Success.getMessage());
        keyResponse.put(MessageType.Data.getMessage(), catList);
        keyResponse.put(MessageType.CurrentPage.getMessage(), cat.getNumber());
        keyResponse.put(MessageType.TotalItems.getMessage(), cat.getTotalElements());
        keyResponse.put(MessageType.TotalPages.getMessage(), cat.getTotalPages());
        response.setSuccess(true);
        response.setResponse(keyResponse);
    	}catch(Exception ex) {
    	response.setMsg(ex.getMessage());
    	response.setSuccess(false);
    	}
    	return response;
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

    public BlogCategoryResponse update(UpdateBlogCategoryRequest CatRequest)
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

    public BasicResponse createCategory(@Valid BlogCategoryRequest blog)
    {
    	BlogCategoryResponse response = cacheService.createCategory(blog);
    	BasicResponse res = new BasicResponse();
    	HashMap<String, Object> keyResponse = new HashMap<String, Object>();
    	res.setMsg(MessageType.Success.getMessage());
    	res.setSuccess(true);
    	keyResponse.put(MessageType.Data.getMessage(), response);
        return res;
    }
    public BasicResponse deleteById(Long id)
    {
        BasicResponse response = new BasicResponse();
        try {
            repo.deleteById(id);
            response.setSuccess(true);
            response.setMsg(MessageType.Success.getMessage());
        }catch (Exception e){
            response.setSuccess(false);
            response.setMsg(e.getMessage());
        }
        return response;
    }
}
