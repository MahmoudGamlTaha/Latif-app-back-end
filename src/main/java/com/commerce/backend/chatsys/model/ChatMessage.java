package com.commerce.backend.chatsys.model;

import java.util.Date;

import javax.persistence.Id;

import com.commerce.backend.constants.MessageStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {
	   @Id
	   private String id;
	   private String chatId;
	   private String senderId;
	   private String recipientId;
	   private String senderName;
	   private String recipientName;
	   private String content;
	   private Date timestamp;
	   private MessageStatus status;
}
