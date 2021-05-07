package com.commerce.backend.model.request.blog;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;

@Data
public class BlogRequest {

    @NotBlank
    @Size(min = 3, max = 250)
 //   @Pattern(regexp = "[0-9a-zA-Z #,_]+")
    String title;

    Long category;

    @NotBlank
    @Size(min = 10)
    String description;
    
    @Null
    ArrayList<String> extrnImage;
    
    @Null
    ArrayList<MultipartFile> images;
    
    boolean is_external;

}
