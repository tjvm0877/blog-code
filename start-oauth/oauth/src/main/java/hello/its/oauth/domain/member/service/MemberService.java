package hello.its.oauth.domain.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hello.its.oauth.domain.member.repository.MemberRepository;
import hello.its.oauth.domain.member.dto.SignUpResponse;
import hello.its.oauth.domain.member.domain.Member;
import hello.its.oauth.domain.member.domain.Role;
import hello.its.oauth.domain.member.dto.MemberInfoResponse;
import hello.its.oauth.domain.member.dto.SignUpRequest;
import hello.its.oauth.global.security.service.JwtProvider;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final JwtProvider jwtProvider;
	private final MemberRepository memberRepository;

	@Transactional(readOnly = true)
	public MemberInfoResponse getMemberInfoByEmail(String email) {
		Member member = memberRepository.findByEmail(email).orElseThrow(IllegalArgumentException::new);
		return MemberInfoResponse.from(member);
	}

	@Transactional
	public SignUpResponse register(SignUpRequest request) {
		String emailFromToken = jwtProvider.getEmailFromToken(request.token());
		Member member = memberRepository.findByEmail(emailFromToken).orElseThrow(IllegalArgumentException::new);

		String accessToken = jwtProvider.generateAuthToken(member.getEmail(), member.getRole());
		if (member.getRole() == Role.GUEST) {
			member.registerMember(request.nickName());
		}
		return new SignUpResponse(accessToken);
	}
}
