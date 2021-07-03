package com.commerce.backend.model.response;

import java.util.HashMap;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasicResponse {
  private String msg;
  private String message;
  private boolean success;
  public void setMsg(String msg) {
	  this.message = msg;
	  this.msg = msg;
  }
  private HashMap<String, Object> response;
}
