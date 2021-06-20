package com.commerce.backend.helper;

import java.util.Map;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Data;

@Data
public class ChatHistoryRequest {
	@NotNull
	@Positive
	private Long sender;
	
	@NotNull
	@Positive
	private Long reciver;
	
	@NotNull
	@Positive
	private Long ads;
}