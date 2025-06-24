package com.its.refresh_token.exception;

public class TokenNotFoundException extends ServiceException {

	public TokenNotFoundException() {
		super(ErrorCode.TOKEN_MISSING);
	}
}
