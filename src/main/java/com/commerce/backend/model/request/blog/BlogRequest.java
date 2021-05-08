package com.commerce.backend.model.request.blog;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Data
public class BlogRequest {

    @NotBlank
    @Size(min = 3, max = 250)
    String title;
    
    @Positive
    Long category;
    
    Long userId; 
    
    @NotBlank
    @Size(min = 5)
    String description;
    
   
    ArrayList<String> extrnImage ;
    
    @JsonIgnore
    ArrayList<MultipartFile> images;
    
    boolean is_external;

}
