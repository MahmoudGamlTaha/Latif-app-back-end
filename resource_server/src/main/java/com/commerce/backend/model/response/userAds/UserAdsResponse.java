package com.commerce.backend.model.response.userAds;

import com.commerce.backend.model.dto.UserAdsVO;
import com.commerce.backend.model.response.BasicResponse;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAdsResponse extends BasicResponse {
   UserAdsVO userAdsVo;
}