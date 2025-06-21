package com.hello.refresh_token.domain.member.dto;

public record MemberLoginRequest(
	String email,
	String password
) {
}
