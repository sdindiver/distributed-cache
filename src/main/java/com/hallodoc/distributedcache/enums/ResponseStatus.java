package com.hallodoc.distributedcache.enums;

public enum ResponseStatus {
	SUCCESS("success"), FAILURE("failure");

	private String status;

	ResponseStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}