package com.commerce.backend.service.cache;

import com.commerce.backend.constants.MessageType;
import com.commerce.backend.constants.SystemConstant;
import com.commerce.backend.converter.blog.BlogResponseConverter;
import com.commerce.backend.dao.BlogCategoryRepository;
import com.commerce.backend.dao.BlogImageRepository;
import com.commerce.backend.dao.BlogRepository;
import com.commerce.backend.dao.UserRepository;
import com.commerce.backend.error.exception.ResourceNotFoundException;
import com.commerce.backend.model.entity.Blog;
import com.commerce.backend.model.entity.BlogCategory;
import com.commerce.backend.model.entity.BlogImage;
import com.commerce.backend.model.entity.User;
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
    private final UserRepository userRepository; 
    @Value("${swagger.host.path}")
    private String path;

    @Autowired
    public BlogCacheServiceImpl(BlogRepository blogRepository, BlogCategoryRepository blogCategoryRepository, FilesStorageService storageService, BlogImageRepository blogImageRepository, BlogResponseConverter converter,UserRepository userRepository) {
        this.blogRepository = blogRepository;
        this.blogCategoryRepository = blogCategoryRepository;
        this.storageService = storageService;
        this.blogImageRepository = blogImageRepository;
        this.converter = converter;
		this.userRepository = userRepository;
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

    public Page<Blog> findByCategory(Long category, Pageable pageable)
    {
        BlogCategory blogCategory = blogCategoryRepository.findById(category).orElse(null);
        if(blogCategory != null) {
            return blogRepository.findByCategory(blogCategory, pageable);
        }
        return null;
    }
    @Override
    //@Cacheable(key = "#root.methodName")
    public BasicResponse saveBlog(BlogRequest blog, List<String> paths, boolean external,  List<MultipartFile> files)
    {
    	BasicResponse response = new BasicResponse();
    	HashMap<String, Object> hashMap = new HashMap<String, Object>();
        BlogCategory category = blogCategoryRepository.findById(blog.getCategory()).orElse(null);
        User user = this.userRepository.findById(blog.getUserId()).orElse(null);
        Blog entity = Blog.builder()
                .title(blog.getTitle())
                .description(blog.getDescription())
                .category(category)
                .active(true)
                .userId(user)
                .path(path + "blogs")
                .externalLink(external)
                .date(new Date())
                .created_at(new Date())
                .build();
        Set<BlogImage> blogImages = new HashSet<BlogImage>();
        if(external && paths!= null){
            entity.setPath(String.valueOf(paths));
            paths.forEach(path -> {
            BlogImage image = new BlogImage();
            image.setBlog(entity);
            image.setCreatedAt(new Date());
            image.setExternalLink(entity.isExternalLink());
            image.setImage(path);
            blogImages.add(image);
            entity.setImage(path);
            });
        }
        if(files != null && !external) {
            files.forEach(imageFile -> {BlogImage image = new BlogImage();
            image.setBlog(entity);
            image.setCreatedAt(new Date());
            image.setExternalLink(entity.isExternalLink());
            String fileName = storageService.save(imageFile);
            if(entity.getImage() == null) {
            	 entity.setImage(fileName); 	
            }
            image.setImage(fileName);
            blogImages.add(image);
            });
           
        }
        entity.setBlogImage(blogImages);
        Blog blogEntity = blogRepository.save(entity);
        BlogResponse basicResponse = converter.apply(blogEntity);
      /*  if(files != null) {
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
        }*/
        hashMap.put(MessageType.Data.getMessage(), basicResponse);
        response.setResponse(hashMap);
        response.setSuccess(true);
        return response ;
    }

    @Override
    public BlogResponse update(UpdateBlogRequest blogRequest, boolean external, List<MultipartFile> images, List<String> externImage ) throws IOException {
        Blog blog = blogRepository.findById(blogRequest.getId()).orElse(null);
        if(blog != null) {
            if (blogRequest.getTitle() != null) {
                blog.setTitle(blogRequest.getTitle());
            }
            if (blogRequest.getDescription() != null) {
                blog.setDescription(blogRequest.getDescription());
            }
            Set<BlogImage> fileImages = new HashSet<BlogImage>();
            if (images != null && !external) {
                if(blog.getImage() != null){
                    storageService.delete(blog.getImage());
                }
                images.stream().forEach(img ->{
                	 String fileName = storageService.save(img);
                	 BlogImage image = new BlogImage();
                	 image.setBlog(blog);
                	 image.setImage(fileName);
                	 image.setExternalLink(external);
                	 fileImages.add(image);
                });
            }
            if(external){
            	
            	externImage.forEach(path -> {
                BlogImage image = new BlogImage();
                image.setBlog(blog);
                image.setCreatedAt(new Date());
                image.setExternalLink(blog.isExternalLink());
                image.setImage(path);
                fileImages.add(image);
                blog.setImage(path);
                });
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
