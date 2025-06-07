package hello.its.oauth.global.security.authentication;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

	private final String accessToken;
	private final String email;

	public JwtAuthenticationToken(
		Collection<? extends GrantedAuthority> authorities, String accessToken, String email) {
		super(authorities);
		this.accessToken = accessToken;
		this.email = email;
		setAuthenticated(true);
	}

	public JwtAuthenticationToken(String accessToken) {
		super(null);
		this.accessToken = accessToken;
		this.email = "";
		setAuthenticated(false);
	}

	public static JwtAuthenticationToken unAuthenticated(String accessToken) {
		return new JwtAuthenticationToken(accessToken);
	}

	public static JwtAuthenticationToken authenticated(String accessToken, String email,
		Collection<? extends GrantedAuthority> authorities) {
		return new JwtAuthenticationToken(authorities, accessToken, email);
	}

	@Override
	public Object getCredentials() {
		return "";
	}

	@Override
	public String getPrincipal() {
		return "";
	}

	public String getEmail() {
		return email;
	}

	public String getAccessToken() {
		return accessToken;
	}
}
