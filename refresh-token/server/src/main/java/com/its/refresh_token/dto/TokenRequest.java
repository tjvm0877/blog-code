package com.its.refresh_token.dto;

public record TokenRequest(
	long accessTokenDuration,
	long refreshTokenDuration
) {
}
