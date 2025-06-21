package com.hello.refresh_token.domain.member.dto;

import com.hello.refresh_token.domain.member.domain.Member;

public record MemberInfoResponse(
	String email
) {
	public static MemberInfoResponse from(Member member) {
		return new MemberInfoResponse(member.getEmail());
	}
}
