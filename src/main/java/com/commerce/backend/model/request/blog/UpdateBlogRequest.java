package com.commerce.backend.model.request.blog;

import com.sun.istack.NotNull;
import lombok.Data;

import java.util.Set;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateBlogRequest {

    @NotNull
    @Positive
    Long id;
    
    Long category;
    
    String title;

    String description;
    
    Boolean active;  
}
