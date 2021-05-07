package com.commerce.backend.model.request.userAds;

import com.commerce.backend.constants.AdsType;

import lombok.Builder.Default;
import lombok.Data;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Data
public class DynamicAdsRequest<T,R>  {

    private AdsType type;
    Boolean external;
 //   String channel;
    private List<HashMap<T,R>> userAds = new LinkedList<>();
    
}
