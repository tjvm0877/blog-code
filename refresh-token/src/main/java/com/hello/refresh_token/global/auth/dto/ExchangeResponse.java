package com.hello.refresh_token.global.auth.dto;

public record ExchangeResponse(
	String accessToken,
	String refreshToken
) {
}
