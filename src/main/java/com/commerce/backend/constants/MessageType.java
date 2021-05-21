package com.commerce.backend.constants;

public enum MessageType {
	Success("SUCCESS"),
	Fail("Fail"),
	Data("data"),
    Missing("Data Missing"),
	CurrentPage("currentPage"),
	TotalItems("totalItems"),
	NotAuthorized("notAuthorized"),
	TotalPages("totalPages");
	private String message;
	
	public String getMessage() {
		return this.message;
	}
	 MessageType(String message) {
		this.message = message;
	}
}
