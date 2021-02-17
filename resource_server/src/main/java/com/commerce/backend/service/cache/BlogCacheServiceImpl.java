package com.commerce.backend.service.cache;

import com.commerce.backend.dao.BlogRepository;
import com.commerce.backend.model.entity.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "blog")
public class BlogCacheServiceImpl implements BlogCacheService{

    private final BlogRepository blogRepository;

    @Autowired
    public BlogCacheServiceImpl(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
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
    public Blog findById(Long id)
    {
        return blogRepository.findById(id).orElse(null);
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
    public Blog deleteBlog(Long id) {
        Optional<Blog> Blog = this.blogRepository.findById(id);
        this.blogRepository.deleteById(id);
        return Blog.orElse(null);
    }
}
