package com.commerce.backend.model.request.blog;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@Data
public class UpdateBlogRequest {

    @NotNull
    @Positive
    Long id;

    @Pattern(regexp = "[0-9a-zA-Z #,_]+")
    String title;

    String description;

}
