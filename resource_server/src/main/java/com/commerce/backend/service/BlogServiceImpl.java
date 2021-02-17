package com.commerce.backend.service;

import com.commerce.backend.converter.blog.BlogResponseConverter;
import com.commerce.backend.dao.BlogCategoryRepository;
import com.commerce.backend.dao.BlogRepository;
import com.commerce.backend.error.exception.ResourceNotFoundException;
import com.commerce.backend.model.dto.BlogCategoryDTO;
import com.commerce.backend.model.dto.BlogDTO;
import com.commerce.backend.model.entity.Blog;
import com.commerce.backend.model.entity.BlogCategory;
import com.commerce.backend.model.entity.User;
import com.commerce.backend.model.request.blog.BlogRequest;
import com.commerce.backend.model.response.blog.BlogResponse;
import com.commerce.backend.service.cache.BlogCacheServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
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
    public Blog getBlogById(Long id)
    {
        Blog getBlog = blogCacheService.findById(id);

        if(Objects.isNull(getBlog))
        {
            throw new ResourceNotFoundException("Not Found");
        }

        return getBlog;
    }


    /**
     *
     * @param title
     * @return
     * return blog by title
     */
    public List<BlogResponse> getBlogByTitle(String title)
    {
        List<Blog> blogList = blogCacheService.getBlogByTitle(title);
        return blogList
                .stream()
                .map(blogResponseConverter)
                .collect(Collectors.toList());
    }


    /**
     *
     * @param blog
     * @return
     * save blog
     */
    public Blog saveBlog(BlogRequest blog)
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
        return blogRepository.save(entity);
    }


    /**
     *
     * @param id
     * @return
     */
    public BlogResponse deleteBlog(Long id)
    {
        Blog blog = blogCacheService.deleteBlog(id);
        BlogResponse blogResponse = new BlogResponse();
        BlogDTO blogDTO = new BlogDTO(blog);
        blogResponse.setBlog(blogDTO);
        return blogResponse;
    }
}