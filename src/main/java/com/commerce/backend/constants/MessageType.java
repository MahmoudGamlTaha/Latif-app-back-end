package com.commerce.backend.constants;

public enum MessageType {
	Success("SUCCESS"),
	Fail("Fail"),
	Data("data"),
	CurrentPage("currentPage"),
	TotalItems("totalItems"),
	TotalPages("totalPages")
	;
	private String message;
	
	public String getMessage() {
		return this.message;
	}
	 MessageType(String message) {
		this.message = message;
	}
}
