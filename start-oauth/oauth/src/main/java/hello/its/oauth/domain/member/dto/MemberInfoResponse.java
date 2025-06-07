package hello.its.oauth.domain.member.dto;

import hello.its.oauth.domain.member.domain.Member;

public record MemberInfoResponse(
	String email,
	String name,
	String profile
) {
	public static MemberInfoResponse from(Member member) {
		return new MemberInfoResponse(member.getEmail(), member.getName(), member.getProfileImage());
	}
}
