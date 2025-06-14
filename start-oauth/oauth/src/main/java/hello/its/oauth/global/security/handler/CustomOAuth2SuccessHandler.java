package hello.its.oauth.global.security.handler;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import hello.its.oauth.domain.member.domain.Member;
import hello.its.oauth.domain.member.repository.MemberRepository;
import hello.its.oauth.domain.member.domain.Role;
import hello.its.oauth.global.security.constants.OAuthProvider;
import hello.its.oauth.global.security.info.OAuthUserInfo;
import hello.its.oauth.global.security.info.OAuthUserInfoFactory;
import hello.its.oauth.global.security.service.JwtProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * OAuth2 인증 성공 시 처리 로직을 담당하는 핸들러
 * - 사용자 정보 조회/생성
 * - JWT 임시 토큰 발급
 * - 역할에 따른 리다이렉트 처리
 */
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final JwtProvider jwtProvider; // JWT 생성 유틸리티
	private final MemberRepository memberRepository;
	private static final String CLIENT_BASE_URL = "http://localhost:3000"; // 프론트엔드 Base URL
	private static final String SIGN_UP_PATH = "/sign-up"; // 추가 정보 입력 페이지
	private static final String AUTH_PATH = "/callback"; // 인증 완료 페이지

	// OAuth2 인증 성공 시 호출되는 메인 메서드
	@Override
	@Transactional
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {

		// 1. OAuth2 사용자 정보 추출
		OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
		OAuthProvider provider = getProvider(authentication); // 제공자(KAKAO, GOOGLE 등) 추출
		Map<String, Object> attributes = oAuth2User.getAttributes();

		// 2. 공통 사용자 정보 객체 생성
		OAuthUserInfo oAuthUserInfo = OAuthUserInfoFactory.getOAuthUserInfo(provider, attributes);

		// 3. 회원 정보 조회/생성(신규 사용자: GUEST, 기존 사용자: USER)
		Member member = getMember(oAuthUserInfo);

		// 4. 임시 JWT 토큰 생성
		String token = jwtProvider.generateTempToken(member.getEmail(), member.getRole());

		// 5. 역할에 따른 리다이렉트 URL 생성
		String redirectUrl = getRedirectUrlByRole(member.getRole(), token);
		getRedirectStrategy().sendRedirect(request, response, redirectUrl);
	}

	/**
	 * 회원 정보 조회 및 생성 로직
	 * - 존재하지 않는 경우: GUEST 역할로 임시 회원 생성
	 * - 존재하는 경우: 기존 회원 정보 반환
	 */
	private Member getMember(OAuthUserInfo userInfo) {
		Optional<Member> optionalMember = memberRepository.findByIdentifier(userInfo.getProviderIdentifier());
		if (optionalMember.isEmpty()) {
			// 신규 사용자: 기본 정보로 임시 회원 생성
			Member guestMember = Member.builder()
				.role(Role.GUEST) // 초기 권한: GUEST
				.nickname("") // 추가 정보 입력 필요
				.provider(userInfo.getOAuthProvider()) // 인증 제공자
				.identifier(userInfo.getProviderIdentifier()) // 제공자 측 사용자 ID
				.email(userInfo.getEmail())
				.name(userInfo.getName())
				.profileImage(userInfo.getProfileImage())
				.build();
			return memberRepository.save(guestMember);
		}
		return optionalMember.get();
	}

	/**
	 * OAuth 제공자 추출 메서드
	 * - OAuth2AuthenticationToken에서 제공자 정보 파싱
	 */
	private OAuthProvider getProvider(Authentication authentication) {
		if (authentication instanceof OAuth2AuthenticationToken token) {
			return OAuthProvider.valueOf(token.getAuthorizedClientRegistrationId().toUpperCase());
		}
		throw new IllegalArgumentException("Cannot extract provider from authentication");
	}

	/**
	 * 역할에 따른 리다이렉트 URL 생성
	 * - GUEST: 추가 정보 입력 페이지(/sign-up)
	 * - USER: 인증 완료 페이지(/callback)
	 * - 토큰을 쿼리 파라미터로 전달
	 */
	private String getRedirectUrlByRole(Role role, String token) {
		String path = (role == Role.GUEST) ? SIGN_UP_PATH : AUTH_PATH;
		return UriComponentsBuilder.fromUriString(CLIENT_BASE_URL + path)
			.queryParam("token", token)  // 임시 토큰 전달
			.build()
			.toUriString();
	}
}