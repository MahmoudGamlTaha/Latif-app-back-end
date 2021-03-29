package com.commerce.backend.service.cache;

import com.commerce.backend.constants.SystemConstant;
import com.commerce.backend.dao.BlogCategoryRepository;
import com.commerce.backend.dao.BlogRepository;
import com.commerce.backend.error.exception.ResourceNotFoundException;
import com.commerce.backend.model.entity.Blog;
import com.commerce.backend.model.entity.BlogCategory;
import com.commerce.backend.model.request.blog.BlogRequest;
import com.commerce.backend.model.request.blog.UpdateBlogRequest;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.model.response.blog.BlogResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


import static java.lang.System.currentTimeMillis;

@Service
@CacheConfig(cacheNames = "blog")
public class BlogCacheServiceImpl implements BlogCacheService{

    private final BlogRepository blogRepository;
    private final BlogCategoryRepository blogCategoryRepository;
    private final Path rootLocation = Paths.get("upload");
    @Value("${swagger.host.path}")
    private String path;

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
    //@Cacheable(key = "#root.methodName")
    public Page<Blog> findAll(Integer page)
    {
//    	Optional<String> sortBy ;

    	 Pageable offset =  PageRequest.of(page, SystemConstant.MOBILE_PAGE_SIZE);
        return blogRepository.findAll(offset);
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
        return new BlogResponse(blog);
    }


    /**
     *
     * @param keyword
     * @return
     * return blog by title
     */
    @Override
    @Cacheable(key = "#keyword", unless = "{#root.caches[0].get(#keyword) == null, #result.equals(null)}")
    public List<Blog> search(String keyword)
    {
        return blogRepository.findAll(keyword);
    }


    @Override
    //@Cacheable(key = "#root.methodName")
    public BlogResponse saveBlog(BlogRequest blog, MultipartFile file)
    {
        String filename = currentTimeMillis()+"-"+StringUtils.cleanPath(file.getOriginalFilename());
        filename = filename.toLowerCase().replaceAll(" ", "-");
        try {
            Files.copy(file.getInputStream(), rootLocation.resolve(filename));
        } catch (Exception e) {
          e.printStackTrace();
        }
        BlogCategory category = blogCategoryRepository.findById(blog.getCategory()).orElse(null);
        Blog entity = Blog.builder()
                .title(blog.getTitle())
                .description(blog.getDescription())
                .category(category)
                .path(path + "blogs")
                .image("upload/"+filename)
                .date(new Date())
                .created_at(new Date())
                .build();
        Blog test = blogRepository.save(entity);
        return new BlogResponse(test);
    }

    @Override
    public BlogResponse update(UpdateBlogRequest blogRequest, MultipartFile file) throws IOException {

        if(blogRequest.getId() != null) {
            Blog blog = blogRepository.findById(blogRequest.getId()).orElse(null);
            if (blogRequest.getTitle() != null) {
                blog.setTitle(blogRequest.getTitle());
            }
            if (blogRequest.getDescription() != null) {
                blog.setDescription(blogRequest.getDescription());
            }
            if (file != null) {
                Path imagesPath = Paths.get(path + blog.getImage());
                Files.delete(imagesPath);
                String filename = currentTimeMillis() + "-" + StringUtils.cleanPath(file.getOriginalFilename());
                filename = filename.toLowerCase().replaceAll(" ", "-");
                try {
                    Files.copy(file.getInputStream(), rootLocation.resolve(filename));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                blog.setImage("upload/" + filename);
            }
            assert blog != null;
            blog.setUpdated_at(new Date());
            blogRepository.save(blog);
            return new BlogResponse(blog);
        }else{
            throw new ResourceNotFoundException("Not Found");
        }
    }

    @Override
    public BasicResponse deleteBlog(Long id) {
        BasicResponse response = new BasicResponse();
        try {
            blogRepository.deleteById(id);
            response.setSuccess(true);
            response.setMsg("Removed");
        }catch (Exception e){
            response.setSuccess(false);
            response.setMsg("Error: "+ e);
        }
        return response;
    }
}
