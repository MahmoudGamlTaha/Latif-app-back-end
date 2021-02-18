package com.commerce.backend.service.cache;

import com.commerce.backend.dao.BlogCategoryRepository;
import com.commerce.backend.dao.BlogRepository;
import com.commerce.backend.error.exception.ResourceNotFoundException;
import com.commerce.backend.model.entity.Blog;
import com.commerce.backend.model.entity.BlogCategory;
import com.commerce.backend.model.request.blog.BlogRequest;
import com.commerce.backend.model.response.blog.BlogResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "blog")
public class BlogCacheServiceImpl implements BlogCacheService{

    private final BlogRepository blogRepository;
    private final BlogCategoryRepository blogCategoryRepository;

    @Autowired
    public BlogCacheServiceImpl(BlogRepository blogRepository, BlogCategoryRepository blogCategoryRepository) {
        this.blogRepository = blogRepository;
        this.blogCategoryRepository = blogCategoryRepository;
    }

    /**
     *
     * @return
     * return All Blogs
     */
    @Override
    @Cacheable(key = "#root.methodName")
    public List<Blog> findAll()
    {
        return blogRepository.findAll();
    }


    /**
     *
     * @param id
     * id type Long
     * @return
     * return Blog By Id
     */
    @Override
    @Cacheable(key = "#root.methodName")
    public BlogResponse findById(Long id)
    {
        Blog blog = blogRepository.findById(id).orElse(null);
        if(Objects.isNull(blog))
        {
            throw new ResourceNotFoundException("Not Found");
        }
        BlogResponse blogResponse = new BlogResponse(blog);
        return blogResponse;
    }


    /**
     *
     * @param title
     * @return
     * return blog by title
     */
    @Override
    @Cacheable(key = "#root.methodName")
    public List<Blog> getBlogByTitle(String title)
    {
        return blogRepository.getBlogByTitle(title);
    }


    @Override
    @Cacheable(key = "#root.methodName")
    public BlogResponse saveBlog(BlogRequest blog)
    {
        BlogCategory category = blogCategoryRepository.findByName(blog.getCategory()).orElse(null);
        Blog entity = Blog.builder()
                .title(blog.getTitle())
                .description(blog.getDescription())
                .category(category)
                .image(blog.getImage())
                .path(blog.getPath())
                .date(new Date())
                .created_at(new Date())
                .build();
        Blog test = blogRepository.save(entity);
        return new BlogResponse(test);
    }

    @Override
    public String deleteBlog(Long id) {
        Optional<Blog> Blog = this.blogRepository.findById(id);
        this.blogRepository.deleteById(id);
        return "Blog Removed";
    }
}
