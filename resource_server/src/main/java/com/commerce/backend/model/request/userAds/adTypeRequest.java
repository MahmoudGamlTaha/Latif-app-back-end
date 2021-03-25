package com.commerce.backend.model.request.userAds;

import com.commerce.backend.constants.AdsType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class adTypeRequest {

    @NotBlank
    @Size(min = 3, max = 80)
    @Pattern(regexp = "[0-9a-zA-Z #,-,_]+")
    private AdsType adsType;
}
