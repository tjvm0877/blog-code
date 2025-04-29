package com.its.auth_with_annotation.dto;

import com.its.auth_with_annotation.domain.Member;

public record MemberInfo(
	String email
) {
	public static MemberInfo from(Member member) {
		return new MemberInfo(member.getEmail());
	}
}
