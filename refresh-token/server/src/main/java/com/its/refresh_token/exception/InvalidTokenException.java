package com.its.refresh_token.exception;

public class InvalidTokenException extends ServiceException {

	public InvalidTokenException() {
		super(ErrorCode.TOKEN_INVALID);
	}
}
