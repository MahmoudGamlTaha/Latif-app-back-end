package com.commerce.backend.converter.blog;

import com.commerce.backend.converter.user.UserResponseConverter;
import com.commerce.backend.model.entity.Blog;
import com.commerce.backend.model.response.blog.BlogResponse;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class BlogResponseConverter implements Function<Blog, BlogResponse> {

    @Override
    public BlogResponse apply(Blog blog) {
  
        BlogResponse blogResponse = null;
        if(blog != null) {
		    blogResponse = new BlogResponse();
		    blogResponse.setId(blog.getId());
		    blogResponse.setTitle(blog.getTitle());
		    if(blog.getCategory() != null) {
		    blogResponse.setCategory(blog.getCategory().getName());
		    }
		    blogResponse.setDescription(blog.getDescription());
		    blogResponse.setImage(blog.getImage());
		    blogResponse.setPath(blog.getPath());
		    blogResponse.setUser(new UserResponseConverter().apply(blog.getUserId()));
		    blogResponse.setCreatedDate(blog.getCreated_at());
        }
        return blogResponse;
    }
}
