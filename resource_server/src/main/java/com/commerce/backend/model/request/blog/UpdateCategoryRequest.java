package com.commerce.backend.model.request.blog;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
public class UpdateCategoryRequest {

    @NotNull
    @Positive
    Long id;

    @Size(min = 3, max = 250)
    @Pattern(regexp = "[0-9a-zA-Z #,_]+")

    String name;

    String description;
}
