package com.commerce.backend.model.event;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class updateCategoryRequest {

    Long id;

    @NotBlank
    @Size(min = 3, max = 250)
    @Pattern(regexp = "[0-9a-zA-Z #,_]+")
    String name;

    String description;
}
