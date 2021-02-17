package com.commerce.backend.model.request.userAds;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserServiceAdsRequest extends UserAdsRequest{
      private Long category_type_id;
      private Long category_id;
}
