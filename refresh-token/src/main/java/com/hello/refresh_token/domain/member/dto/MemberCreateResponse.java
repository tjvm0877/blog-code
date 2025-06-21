package com.hello.refresh_token.domain.member.dto;

public record MemberCreateResponse(
	String accessToken,
	String refreshToken
) {
}
