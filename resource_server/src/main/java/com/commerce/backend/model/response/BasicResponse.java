package com.commerce.backend.model.response;

import java.util.HashMap;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasicResponse {
  private String msg;
  private boolean success;
  private HashMap<String, Object> response;
}
