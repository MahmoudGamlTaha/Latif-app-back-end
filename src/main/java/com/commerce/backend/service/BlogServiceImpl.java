package com.commerce.backend.service;

import com.commerce.backend.constants.MessageType;
import com.commerce.backend.converter.blog.BlogResponseConverter;
import com.commerce.backend.dao.BlogCategoryRepository;
import com.commerce.backend.dao.BlogRepository;
import com.commerce.backend.model.entity.Blog;
import com.commerce.backend.model.request.blog.BlogRequest;
import com.commerce.backend.model.request.blog.UpdateBlogRequest;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.model.response.blog.BlogResponse;
import com.commerce.backend.service.cache.BlogCacheServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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
    public BasicResponse getBlogs(Integer page)
    {
    	BasicResponse response = new BasicResponse();
    	HashMap<String, Object> hashMapResponse = new HashMap<String, Object>();
      try {
            Page<Blog> blogList = blogCacheService.findAll(page);
        
            List<BlogResponse> blogResponse =  blogList.get()
                   .map(blogResponseConverter)
                   .collect(Collectors.toList());
        	hashMapResponse.put(MessageType.Data.getMessage(), blogResponse);
        	hashMapResponse.put(MessageType.CurrentPage.getMessage(), blogList.getNumber());
        	hashMapResponse.put(MessageType.TotalItems.getMessage(), blogList.getTotalElements());
        	hashMapResponse.put(MessageType.TotalPages.getMessage(), blogList.getTotalPages());
        	

            response.setResponse(hashMapResponse);
    	}catch(Exception ex) {
    		response.setMsg(ex.getMessage());
    		hashMapResponse.put(MessageType.Data.getMessage(), ex.getStackTrace());
    		response.setResponse(hashMapResponse);
    	}
      return response;
    }


    /**
     *
     * @param id
     * id type Long
     * @return
     * return Blog By Id
     */
    @Override
    public BasicResponse getBlogById(Long id)
    {
        BasicResponse response = new BasicResponse();
        HashMap<String, Object> hashMapResponse = new HashMap<String, Object>();
        try {
            BlogResponse blog = new BlogResponse(blogCacheService.findById(id));
            hashMapResponse.put(MessageType.Data.getMessage(), blog);
            response.setSuccess(true);
            response.setResponse(hashMapResponse);
        }catch(Exception ex) {
            response.setMsg(ex.getMessage());
            hashMapResponse.put(MessageType.Data.getMessage(), ex.getStackTrace());
            response.setResponse(hashMapResponse);
        }
        return response;
    }


    /**
     *
     * @param keyword
     * @return
     * return blog by title
     */
    @Override
    public BasicResponse search(String keyword, Pageable pageable)
    {
    	 BasicResponse response = new BasicResponse();
        HashMap<String, Object> hashMapResponse = new HashMap<String, Object>();
    	try {
            if(keyword != null) {
                Page<Blog> blogList = blogCacheService.search(keyword, pageable);
                List<BlogResponse> blogResponse =  blogList.get()
                            .map(blogResponseConverter)
                            .collect(Collectors.toList());
                hashMapResponse.put(MessageType.Data.getMessage(), blogResponse);
                hashMapResponse.put(MessageType.CurrentPage.getMessage(), blogList.getNumber());
                hashMapResponse.put(MessageType.TotalItems.getMessage(), blogList.getTotalElements());
                hashMapResponse.put(MessageType.TotalPages.getMessage(), blogList.getTotalPages());
                response.setSuccess(true);
                response.setResponse(hashMapResponse);
            }
    	} catch(Exception ex) {
    		response.setMsg(ex.getMessage());
    		response.setSuccess(false);
    	}
        return response;
    }


    /**
     *
     * @param blog
     * @return
     * save blog
     */
    @Override
    public BasicResponse saveBlog(BlogRequest blog, List<String> externFiles, List<MultipartFile> files, boolean external)
    {
        return blogCacheService.saveBlog(blog, externFiles, external, files);
    }

    @Override
    public BasicResponse update(UpdateBlogRequest blogRequest, MultipartFile file) throws IOException {
        BasicResponse response = new BasicResponse();
        HashMap<String, Object> hashMapResponse = new HashMap<String, Object>();
        try {
            BlogResponse blog = blogCacheService.update(blogRequest, file);
            hashMapResponse.put(MessageType.Data.getMessage(), blog);
            response.setSuccess(true);
            response.setResponse(hashMapResponse);
        }catch(Exception ex) {
            response.setMsg(ex.getMessage());
            hashMapResponse.put(MessageType.Data.getMessage(), ex.getStackTrace());
            response.setResponse(hashMapResponse);
        }
        return response;
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public BasicResponse deleteBlog(Long id)
    {
        return blogCacheService.deleteBlog(id);
    }
}
