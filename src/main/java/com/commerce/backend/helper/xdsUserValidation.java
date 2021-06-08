package com.commerce.backend.helper;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.commerce.backend.service.UserService;
import com.commerce.backend.model.entity.SubscriptionTypes;
@Component
public class xdsUserValidation {
  @Autowired
   UserService userService;
  public boolean checkCreateAdsAvalibilty() {
	  Set<SubscriptionTypes>  subscription  = this.userService.getCurrentUser().getSubscriptions();
	//  subscription.stream().filter(subs -> subs.geta)
	  /*if(this.userService.getCurrentUser().getAds().size() <= ) {
		  
	  }*/
	 
	  return true;
  }
}
