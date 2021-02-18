package com.commerce.backend.model.response.blog;

import com.commerce.backend.model.entity.Blog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BlogResponse {
    private Long id;
    private String title;
    private String category;
    private String description;
    private String image;
    private String path;

    public BlogResponse(Blog blog) {
        this.id = blog.getId();
        this.title = blog.getTitle();
        this.category = blog.getCategory().getName();
        this.description = blog.getDescription();
        this.image = blog.getImage();
        this.path = blog.getPath();
    }
}
