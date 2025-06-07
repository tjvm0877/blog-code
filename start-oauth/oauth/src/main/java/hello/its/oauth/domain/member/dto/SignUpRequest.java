package hello.its.oauth.domain.member.dto;

public record SignUpRequest(
	String token,
	String nickName
) {
}
