package com.commerce.backend.model.response.blog;

import com.commerce.backend.model.dto.BlogDTO;
import lombok.Data;

@Data
public class BlogResponse {
    private Long id;
    private String category;
    private BlogDTO blog;
}
