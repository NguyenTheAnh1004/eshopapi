package com.eshop.dto.responses;

public class MessageResponse {
	private String Message;

	public MessageResponse(String message) {
		super();
		Message = message;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

}
