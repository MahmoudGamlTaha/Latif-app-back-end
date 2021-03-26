package com.commerce.backend.converter.blog;

import com.commerce.backend.model.dto.BlogCategoryDTO;
import com.commerce.backend.model.entity.BlogCategory;
import com.commerce.backend.model.response.blog.BlogCategoryResponse;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class BlogCategoryResponseConverter implements Function<BlogCategory, BlogCategoryResponse> {

    @Override
    public BlogCategoryResponse apply(BlogCategory blogCategory) {
        BlogCategoryResponse blogCategoryResponse = new BlogCategoryResponse();
        blogCategoryResponse.setId(blogCategory.getId());
        blogCategoryResponse.setName(blogCategory.getName());
        blogCategoryResponse.setDescription(blogCategory.getDescription());
        return blogCategoryResponse;
    }
}
