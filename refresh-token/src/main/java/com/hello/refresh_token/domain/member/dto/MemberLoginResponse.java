package com.hello.refresh_token.domain.member.dto;

public record MemberLoginResponse(
	String accessToken,
	String refreshToken
) {
}
