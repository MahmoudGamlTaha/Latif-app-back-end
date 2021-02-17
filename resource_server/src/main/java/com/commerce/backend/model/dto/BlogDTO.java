package com.commerce.backend.model.dto;

import com.commerce.backend.model.entity.Blog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@AllArgsConstructor
@Builder
public class BlogDTO {
    private String title;
    private String category;
    private String description;
    private String image;
    private String path;

    public BlogDTO(Blog entity) {
        this.category = entity.getCategory().getName();
        this.title = entity.getTitle();
        this.description = entity.getDescription();
        this.image = entity.getImage();
        this.path = entity.getPath();
    }
}
