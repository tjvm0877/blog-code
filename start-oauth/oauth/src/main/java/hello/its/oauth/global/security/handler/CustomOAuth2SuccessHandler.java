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

@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final JwtProvider jwtProvider;
	private final MemberRepository memberRepository;
	private static final String CLIENT_BASE_URL = "http://localhost:3000";
	private static final String SIGN_UP_PATH = "/sign-up";
	private static final String AUTH_PATH = "/callback";

	@Override
	@Transactional
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {
		OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
		OAuthProvider provider = getProvider(authentication);
		Map<String, Object> attributes = oAuth2User.getAttributes();
		OAuthUserInfo oAuthUserInfo = OAuthUserInfoFactory.getOAuthUserInfo(provider, attributes);
		Member member = getMember(oAuthUserInfo);
		String token = jwtProvider.generateTempToken(member.getEmail(), member.getRole());
		String redirectUrl = getRedirectUrlByRole(member.getRole(), token);
		getRedirectStrategy().sendRedirect(request, response, redirectUrl);
	}

	private Member getMember(OAuthUserInfo userInfo) {
		Optional<Member> optionalMember = memberRepository.findByIdentifier(userInfo.getProviderIdentifier());
		if (optionalMember.isEmpty()) {
			Member guestMember = Member.builder()
				.role(Role.GUEST)
				.nickname("")
				.provider(userInfo.getOAuthProvider())
				.identifier(userInfo.getProviderIdentifier())
				.email(userInfo.getEmail())
				.name(userInfo.getName())
				.profileImage(userInfo.getProfileImage())
				.build();
			return memberRepository.save(guestMember);
		}
		return optionalMember.get();
	}

	private OAuthProvider getProvider(Authentication authentication) {
		if (authentication instanceof OAuth2AuthenticationToken token) {
			return OAuthProvider.valueOf(token.getAuthorizedClientRegistrationId().toUpperCase());
		}
		throw new IllegalArgumentException("Cannot extract provider from authentication");
	}

	private String getRedirectUrlByRole(Role role, String token) {
		String path = (role == Role.GUEST) ? SIGN_UP_PATH : AUTH_PATH;
		return UriComponentsBuilder.fromUriString(CLIENT_BASE_URL + path)
			.queryParam("token", token)
			.build()
			.toUriString();
	}
}