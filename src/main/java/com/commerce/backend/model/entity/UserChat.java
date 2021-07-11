package com.commerce.backend.model.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_chats")
public class UserChat {
	
	@Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    private UUID id;
	
	@Column(name = "receiver_id")
	private Long reciverId;
	
	@Column(name = "sender_id")
	private Long senderId;
	
	@Column(name = "message_text")
	private String message;
	
	@Column(name = "ad_id")
	private Long itemId;
	
	@Column(name = "room")
	private String room;
	
	@Column(name = "device_model")
	private String deviceModel;
	
	@Column(name = "read")
	private boolean readed;
	
	@Column(name = "created_at")
	private Date createAt;
	
	@JsonInclude()
	@Transient
	private String reciverName;
	
	@JsonInclude()
	@Transient
	private String reciverImage;
}
