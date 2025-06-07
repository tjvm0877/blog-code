package hello.its.oauth.global.security.service;

import org.springframework.stereotype.Service;

import hello.its.oauth.domain.member.domain.Member;
import hello.its.oauth.domain.member.repository.MemberRepository;
import hello.its.oauth.global.security.dto.TokenRequest;
import hello.its.oauth.global.security.dto.TokenResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final JwtProvider jwtProvider;
	private final MemberRepository memberRepository;

	public TokenResponse validateAndGenerateToken(TokenRequest request) {
		if (!jwtProvider.isValidToken(request.token())) {
			// TODO: 커스텀 예외 사용 필요
			throw new IllegalArgumentException();
		}
		String emailFromToken = jwtProvider.getEmailFromToken(request.token());

		// TODO: 커스텀 예외 필요
		Member member = memberRepository.findByEmail(emailFromToken).orElseThrow(IllegalArgumentException::new);

		return new TokenResponse(jwtProvider.generateAuthToken(member.getEmail(), member.getRole()));
	}
}
