package hello.its.oauth.global.security.provider;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import hello.its.oauth.domain.member.domain.Role;
import hello.its.oauth.global.security.authentication.JwtAuthenticationToken;
import hello.its.oauth.global.security.service.JwtProvider;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthProvider implements AuthenticationProvider {

	private final JwtProvider jwtProvider;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		JwtAuthenticationToken token = (JwtAuthenticationToken)authentication;
		String accessToken = token.getAccessToken();

		if (!jwtProvider.isValidToken(accessToken)) {
			throw new BadCredentialsException("Token is not valid");
		}

		String emailFromJwt = jwtProvider.getEmailFromToken(accessToken);
		Role roleFromJwt = jwtProvider.getRoleFromToken(accessToken);
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(roleFromJwt.toString()));
		return JwtAuthenticationToken.authenticated(accessToken, emailFromJwt, authorities);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return JwtAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
