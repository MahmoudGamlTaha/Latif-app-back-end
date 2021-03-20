package com.commerce.backend.model.request.userAds;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserServiceAdsRequest extends UserAdsRequest{
      private Long category_type_id;
      private Long category_id;
      private Boolean avaliable_at_home;
      private Set<Integer> providedService;
}
