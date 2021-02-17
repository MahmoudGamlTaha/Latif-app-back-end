package com.commerce.backend.converter.blog;

import com.commerce.backend.model.dto.BlogCategoryDTO;
import com.commerce.backend.model.entity.BlogCategory;
import com.commerce.backend.model.response.blog.BlogCategoryResponse;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class BlogCategoryResponseConverter implements Function<BlogCategory, BlogCategoryResponse> {
    @Override
    public BlogCategoryResponse apply(BlogCategory blogCategories) {
        BlogCategoryResponse blogCategoryResponse = new BlogCategoryResponse();
        blogCategoryResponse.setCategory(BlogCategoryDTO.builder()
                .name(blogCategories.getName())
                .description(blogCategories.getDescription())
                .build());
        return blogCategoryResponse;
    }
}
