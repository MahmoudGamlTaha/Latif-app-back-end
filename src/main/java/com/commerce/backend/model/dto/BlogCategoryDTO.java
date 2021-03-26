package com.commerce.backend.model.dto;

import com.commerce.backend.model.entity.Blog;
import com.commerce.backend.model.entity.BlogCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
public class BlogCategoryDTO {
    private String name;
    private String description;

    public BlogCategoryDTO(BlogCategory entity) {
        this.name = getName();
        this.description = getDescription();
    }
}
