package com.commerce.backend.model.response.blog;

import java.util.Date;

import com.commerce.backend.converter.user.UserResponseConverter;
import com.commerce.backend.model.entity.Blog;
import com.commerce.backend.model.response.user.UserResponse;

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
    private UserResponse user;
    private Date createdDate;
    public BlogResponse(Blog blog) {
        this.id = blog.getId();
        this.title = blog.getTitle();
        if(blog.getCategory() != null) {
        this.category = blog.getCategory().getName();
        }
        this.description = blog.getDescription();
        this.image = blog.getImage();
        this.path = blog.getPath();
        
        this.createdDate = blog.getCreated_at();
        this.user = new UserResponseConverter().apply(blog.getUserId());
    }
}