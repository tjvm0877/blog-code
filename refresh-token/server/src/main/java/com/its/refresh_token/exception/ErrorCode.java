package com.its.refresh_token.exception;

public enum ErrorCode {
	INVALID_INPUT_VALUE(400, " Invalid Input Value"),
	INTERNAL_SERVER_ERROR(500, "Server Error"),
	METHOD_NOT_ALLOWED(405, " Invalid Input Value"),

	TOKEN_EXPIRED(400, "Token is Expired"),
	TOKEN_INVALID(400, "Token is Invalid"),
	TOKEN_MISSING(401, "Token is Missing");

	private final int status;
	private final String message;

	ErrorCode(int status, String message) {
		this.status = status;
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}
}
