package com.commerce.backend.model.response.blog;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
    private Long   category_id;
    private String description;
    private String image;
    private Set<String> images;
    private String path;
    private UserResponse user;
    private Date createdDate;
    private boolean externalLink;
    private boolean active;
    public BlogResponse(Blog blog) {
        this.id = blog.getId();
        this.title = blog.getTitle();
        if(blog.getCategory() != null) {
        this.category = blog.getCategory().getName();
        }
        if(blog.getBlogImage() != null) {
        	this.images = new HashSet<String>();
        	blog.getBlogImage().forEach(val -> {
        		if(this.image == null) {
        			this.image = val.getImage();
        		}
        		this.images.add(val.getImage());
        	});
        }
        this.description = blog.getDescription();
       // this.image = blog.getImage();
        this.path = blog.getPath();
        
        this.createdDate = blog.getCreated_at();
        this.user = new UserResponseConverter().apply(blog.getUserId());
    }
}