package com.hallodoc.distributedcache.response;

public class AppResponse {
	private String status;
	private String message;

	public AppResponse(String status, String message) {
		this.status = status;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

}
