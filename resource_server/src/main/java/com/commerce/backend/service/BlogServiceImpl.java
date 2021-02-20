package com.commerce.backend.service;

import com.commerce.backend.converter.blog.BlogResponseConverter;
import com.commerce.backend.dao.BlogCategoryRepository;
import com.commerce.backend.dao.BlogRepository;
import com.commerce.backend.model.dto.BlogCategoryDTO;
import com.commerce.backend.model.entity.Blog;
import com.commerce.backend.model.entity.BlogCategory;
import com.commerce.backend.model.request.blog.BlogRequest;
import com.commerce.backend.model.request.blog.UpdateBlogRequest;
import com.commerce.backend.model.response.blog.BlogResponse;
import com.commerce.backend.service.cache.BlogCacheServiceImpl;
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
public class BlogServiceImpl implements BlogService{

    private final BlogRepository blogRepository;
    private final BlogResponseConverter blogResponseConverter;
    private final BlogCacheServiceImpl blogCacheService;
    private final BlogCategoryRepository blogCategoryRepository;

    @Autowired
    public BlogServiceImpl(BlogRepository blogRepository, BlogResponseConverter blogResponseConverter, BlogCacheServiceImpl blogCacheService, BlogCategoryRepository blogCategoryRepository)
    {
        this.blogCacheService = blogCacheService;
        this.blogResponseConverter = blogResponseConverter;
        this.blogRepository = blogRepository;
        this.blogCategoryRepository = blogCategoryRepository;
    }


    /**
     *
     * @return
     * return All Blogs
     */
    @Override
    public List<BlogResponse> getBlogs()
    {
        List<Blog> getBlogs = blogCacheService.findAll();

        return getBlogs.
                stream()
                .map(blogResponseConverter)
                .collect(Collectors.toList());
    }


    /**
     *
     * @param id
     * id type Long
     * @return
     * return Blog By Id
     */
    @Override
    public BlogResponse getBlogById(Long id)
    {
        return blogCacheService.findById(id);
    }


    /**
     *
     * @param keyword
     * @return
     * return blog by title
     */
    @Override
    public List<BlogResponse> search(String keyword)
    {
        if(keyword != null) {
            List<Blog> blogList = blogCacheService.search(keyword);
            return blogList
                    .stream()
                    .map(blogResponseConverter)
                    .collect(Collectors.toList());
        }
        List<Blog> getBlogs = blogCacheService.findAll();

        return getBlogs.
                stream()
                .map(blogResponseConverter)
                .collect(Collectors.toList());
    }


    /**
     *
     * @param blog
     * @return
     * save blog
     */
    @Override
    public BlogResponse saveBlog(BlogRequest blog)
    {
        return blogCacheService.saveBlog(blog);
    }

    @Override
    public BlogResponse update(UpdateBlogRequest blogRequest)
    {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        SessionFactory factory = configuration.buildSessionFactory();
        Session session = factory.openSession();

        Transaction tx = session.beginTransaction();
        Blog blog = session.load(Blog.class, blogRequest.getId());

        blog.setTitle(blogRequest.getTitle());
        blog.setDescription(blogRequest.getDescription());
        blog.setPath(blogRequest.getPath());

        session.update(blog);
        tx.commit();
        session.close();

        return new BlogResponse(blog);


    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public String deleteBlog(Long id)
    {
        return blogCacheService.deleteBlog(id);
    }
}
