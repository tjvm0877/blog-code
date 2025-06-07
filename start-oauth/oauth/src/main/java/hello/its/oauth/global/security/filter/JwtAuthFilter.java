package hello.its.oauth.global.security.filter;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import hello.its.oauth.global.security.authentication.JwtAuthenticationToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthFilter extends AbstractAuthenticationProcessingFilter {

	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String BEARER = "Bearer ";

	public JwtAuthFilter(RequestMatcher requestMatcher) {
		super(requestMatcher);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
		AuthenticationException {
		String accessToken = Optional.ofNullable(request.getHeader(AUTHORIZATION_HEADER))
			.map(header -> header.substring(BEARER.length()))
			.orElseThrow(() -> new BadCredentialsException("Authorization header is missing"));
		JwtAuthenticationToken token = JwtAuthenticationToken.unAuthenticated(accessToken);
		return this.getAuthenticationManager().authenticate(token);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authResult) throws IOException, ServletException {
		SecurityContextHolder.getContext().setAuthentication(authResult);
		chain.doFilter(request, response);
	}
}
