package com.commerce.backend.service.cache;

import com.commerce.backend.constants.MessageType;
import com.commerce.backend.constants.SystemConstant;
import com.commerce.backend.converter.blog.BlogResponseConverter;
import com.commerce.backend.dao.BlogCategoryRepository;
import com.commerce.backend.dao.BlogImageRepository;
import com.commerce.backend.dao.BlogRepository;
import com.commerce.backend.error.exception.ResourceNotFoundException;
import com.commerce.backend.model.entity.Blog;
import com.commerce.backend.model.entity.BlogCategory;
import com.commerce.backend.model.entity.BlogImage;
import com.commerce.backend.model.request.blog.BlogRequest;
import com.commerce.backend.model.request.blog.UpdateBlogRequest;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.model.response.blog.BlogResponse;

import com.commerce.backend.service.FilesStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@CacheConfig(cacheNames = "blog")
public class BlogCacheServiceImpl implements BlogCacheService{

    private final BlogRepository blogRepository;
    private final BlogCategoryRepository blogCategoryRepository;
    private final FilesStorageService storageService;
    private final BlogImageRepository blogImageRepository;
    private final BlogResponseConverter converter;
    @Value("${swagger.host.path}")
    private String path;

    @Autowired
    public BlogCacheServiceImpl(BlogRepository blogRepository, BlogCategoryRepository blogCategoryRepository, FilesStorageService storageService, BlogImageRepository blogImageRepository, BlogResponseConverter converter) {
        this.blogRepository = blogRepository;
        this.blogCategoryRepository = blogCategoryRepository;
        this.storageService = storageService;
        this.blogImageRepository = blogImageRepository;
        this.converter = converter;
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
    //@Cacheable(key = "#root.methodName")
    public Blog findById(Long id)
    {
        Blog blog = blogRepository.findById(id).orElse(null);
        if(Objects.isNull(blog))
        {
            throw new ResourceNotFoundException("Not Found");
        }
        return blog;
    }


    /**
     *
     * @param keyword
     * @return
     * return blog by title
     */
    @Override
    @Cacheable(key = "#keyword", unless = "{#root.caches[0].get(#keyword) == null, #result.equals(null)}")
    public Page<Blog> search(String keyword, Pageable pageable)
    {
        return blogRepository.findAll(keyword, pageable);
    }


    @Override
    //@Cacheable(key = "#root.methodName")
    public BasicResponse saveBlog(BlogRequest blog, List<String> paths, boolean external,  List<MultipartFile> files)
    {
    	BasicResponse response = new BasicResponse();
    	HashMap<String, Object> hashMap = new HashMap<String, Object>();
        BlogCategory category = blogCategoryRepository.findById(blog.getCategory()).orElse(null);
        Blog entity = Blog.builder()
                .title(blog.getTitle())
                .description(blog.getDescription())
                .category(category)
                .path(path + "blogs")
                .date(new Date())
                .created_at(new Date())
                .build();
        if(external){
            entity.setPath(paths.toString());
        }
        if(files != null) {
            String fileName = storageService.save(files.get(0));
            entity.setImage(fileName);
        }
        Blog blogEntity = blogRepository.save(entity);
        BlogResponse basicResponse = converter.apply(blogEntity);
        if(files != null) {
            if (files.size() > 1) {
                List<BlogImage> ImagesList = new ArrayList<>();
                for (int i = 1; i < files.size(); i++) {
                    String fName = storageService.save(files.get(i));
                    ImagesList.add(BlogImage.builder()
                            .blog(blogEntity)
                            .image(fName)
                            .createdAt(new Date())
                            .build());
                }
                blogImageRepository.saveAll(ImagesList);
            }
        }
        hashMap.put(MessageType.Data.getMessage(), basicResponse);
        response.setResponse(hashMap);
        return response ;
    }

    @Override
    public BlogResponse update(UpdateBlogRequest blogRequest, MultipartFile file) throws IOException {
        Blog blog = blogRepository.findById(blogRequest.getId()).orElse(null);
        if(blog != null) {
            if (blogRequest.getTitle() != null) {
                blog.setTitle(blogRequest.getTitle());
            }
            if (blogRequest.getDescription() != null) {
                blog.setDescription(blogRequest.getDescription());
            }
            if (file != null) {
                if(blog.getImage() != null){
                    storageService.delete(blog.getImage());
                }
                String fileName = storageService.save(file);
                blog.setImage(fileName);
            }
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
