package com.commerce.backend.converter.blog;

import com.commerce.backend.model.dto.BlogDTO;
import com.commerce.backend.model.entity.Blog;
import com.commerce.backend.model.response.blog.BlogResponse;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class BlogResponseConverter implements Function<Blog, BlogResponse> {

    @Override
    public BlogResponse apply(Blog blog) {
        BlogResponse blogResponse = new BlogResponse();
        blogResponse.setId(blog.getId());
        blogResponse.setCategory(blog.getCategory().getName());
        blogResponse.setBlog(BlogDTO.builder()
                .category(blog.getCategory().getName())
                .title(blog.getTitle())
                .description(blog.getDescription())
                .image(blog.getImage())
                .path(blog.getPath())
                .build());
        return blogResponse;
    }
}
