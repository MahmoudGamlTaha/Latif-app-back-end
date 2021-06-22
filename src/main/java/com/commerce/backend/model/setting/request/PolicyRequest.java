package com.commerce.backend.model.setting.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class PolicyRequest {
   @NotNull
   @Positive
	Long id;
   
}
