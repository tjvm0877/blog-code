package com.its.book_info.global.error.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

	INVALID_INPUT_VALUE(400, " Invalid Input Value"),
	METHOD_NOT_ALLOWED(405, " Invalid Input Value"),
	ENTITY_NOT_FOUND(400, " Entity Not Found"),
	INTERNAL_SERVER_ERROR(500, "Server Error"),
	INVALID_TYPE_VALUE(400, " Invalid Type Value"),

	BOOK_NOT_FOUND(400, "Book Not Found");

	private final int status;
	private final String message;

	ErrorCode(int status, String message) {
		this.status = status;
		this.message = message;
	}
}
