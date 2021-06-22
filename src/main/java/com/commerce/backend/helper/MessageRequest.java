package com.commerce.backend.helper;

import lombok.Data;

@Data
public class MessageRequest {
   Long  sender;
   Long  ad_item;
   Long  recevier;
   String message;
   String device_id;
   String device_model;
   String room;
}
