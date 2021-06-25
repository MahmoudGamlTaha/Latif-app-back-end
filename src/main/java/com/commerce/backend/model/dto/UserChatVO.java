package com.commerce.backend.model.dto;

import java.util.Date;
import java.util.UUID;

import lombok.Data;
@Data
public class UserChatVO {
    private UUID id;
	
	private Long reciverId;
	
	private Long senderId;
	
	private String message;
	
	private Long itemId;
	
	private String room;
		
	private Date createAt;
	
	private String reciverName;
	
	private String reciverImage;
}
