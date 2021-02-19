package com.commerce.backend.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasicResponse {
  private String msg;
  public boolean success;
}
