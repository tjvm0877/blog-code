package com.its.refresh_token.exception;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {

	private ErrorCode errorCode;

	public ServiceException(String message, ErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public ServiceException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}
