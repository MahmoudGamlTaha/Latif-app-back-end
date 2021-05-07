package com.commerce.backend.converter.blog;

import com.commerce.backend.converter.user.UserResponseConverter;
import com.commerce.backend.model.entity.Blog;
import com.commerce.backend.model.response.blog.BlogResponse;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.function.Function;

@Component
public class BlogResponseConverter implements Function<Blog, BlogResponse> {

    @Override
    public BlogResponse apply(Blog blog) {
  
       final BlogResponse blogResponse = new BlogResponse();
        if(blog != null) {
		    blogResponse.setId(blog.getId());
		    blogResponse.setTitle(blog.getTitle());
		    if(blog.getCategory() != null) {
		    blogResponse.setCategory(blog.getCategory().getName());
		    blogResponse.setCategory_id(blog.getCategory().getId());
		    }
		    if(blog.getBlogImage() != null) {
		    	blogResponse.setImages(new HashSet<String>());
		    	blog.getBlogImage().forEach(image -> {
		    		  if(blogResponse.getImage() == null) {
		              	blogResponse.setImage(image.getImage()); 
		               }
		    		blogResponse.getImages().add(image.getImage());});
           
		    }
		    
		    blogResponse.setDescription(blog.getDescription());
		    blogResponse.setImage(blog.getImage());
		    blogResponse.setPath(blog.getPath());
		    blogResponse.setExternalLink(blog.isExternalLink());
		    blogResponse.setUser(new UserResponseConverter().apply(blog.getUserId()));
		    blogResponse.setCreatedDate(blog.getCreated_at());
        }
        return blogResponse;
    }
}
