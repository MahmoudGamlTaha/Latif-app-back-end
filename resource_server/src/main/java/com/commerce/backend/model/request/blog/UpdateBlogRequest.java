package com.commerce.backend.model.request.blog;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class UpdateBlogRequest {

    Long id;

    @Pattern(regexp = "[0-9a-zA-Z #,_]+")
    String title;

    String description;

    private String image;

    String path;
}
