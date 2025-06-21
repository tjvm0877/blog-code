package com.hello.refresh_token.domain.member.dto;

import com.hello.refresh_token.domain.member.domain.Member;

public record MemberCreateRequest(
	String email,
	String password
) {
}
