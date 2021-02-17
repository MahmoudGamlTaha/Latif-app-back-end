package com.commerce.backend.model.request.blog;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class BlogRequest {

    @NotBlank
    @Size(min = 3, max = 250)
    @Pattern(regexp = "[0-9a-zA-Z #,_]+")
    String title;

    @NotBlank
    @Size(min = 3, max = 250)
    @Pattern(regexp = "[0-9a-zA-Z #,_]+")
    String category;

    @NotBlank
    @Size(min = 40)
    //@Pattern(regexp = "(?m)^(?=.*(?:\\r?\\n(?!\\r?\\n).*)*?\\bNew-York\\b)(?=.*(?:\\r?\\n(?!\\r?\\n).*)*?\\bBerlin\\b).*(?:\\r?\\n(?!\\r?\\n).*)*")
    String description;

    @NotBlank
    @Size(min = 3, max = 240)
    //@Pattern(regexp = "/([a-z\\-_0-9\\/\\:\\.]*\\.(jpg|jpeg|png|gif))/i")
    private String image;

    @NotBlank
    @Size(min = 3, max = 240)
    //@Pattern(regexp = "[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)")
    String path;
}
