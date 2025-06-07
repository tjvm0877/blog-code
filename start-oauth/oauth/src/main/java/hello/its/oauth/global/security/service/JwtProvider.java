package hello.its.oauth.global.security.service;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import hello.its.oauth.domain.member.domain.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtProvider {

	private static final String BEARER_PREFIX = "Bearer ";
	private final SecretKey secretKey;
	private final Long authTokenExpirationTime;
	private final Long tempTokenExpirationTime;

	public JwtProvider(
		@Value("${jwt.secret}") String SECRET_KEY,
		@Value("${jwt.auth-expiration}") Long AUTH_EXPIRATION_TIME,
		@Value("${jwt.temp-expiration}") Long TEMP_EXPIRATION_TIME
	) {
		if (SECRET_KEY == null || SECRET_KEY.isEmpty()) {
			throw new IllegalArgumentException("JWT secret key must not be null or empty");
		}
		if (AUTH_EXPIRATION_TIME == null || AUTH_EXPIRATION_TIME <= 0) {
			throw new IllegalArgumentException("JWT Auth expiration time must be a positive value");
		}
		if (TEMP_EXPIRATION_TIME == null || TEMP_EXPIRATION_TIME <= 0) {
			throw new IllegalArgumentException("JWT Temp expiration time must be a positive value");
		}
		this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
		this.authTokenExpirationTime = AUTH_EXPIRATION_TIME;
		this.tempTokenExpirationTime = TEMP_EXPIRATION_TIME;
	}

	private String generateToken(String email, Role role, Long expirationTime) {
		long currentTimeMillis = System.currentTimeMillis();
		return Jwts.builder()
			.claim("email", email)
			.claim("role", role)
			.issuedAt(new Date(currentTimeMillis))
			.expiration(new Date(currentTimeMillis + expirationTime))
			.signWith(secretKey)
			.compact();
	}

	public String generateAuthToken(String email, Role role) {
		return generateToken(email, role, authTokenExpirationTime);
	}

	public String generateTempToken(String email, Role role) {
		return generateToken(email, role, tempTokenExpirationTime);
	}

	public boolean isValidToken(String token) {
		try {
			return parseClaims(token).getExpiration().after(new Date());
		} catch (ExpiredJwtException e) {
			log.warn("Token expired: {}", e.getMessage());
			return false;
		} catch (MalformedJwtException e) {
			log.warn("Malformed token: {}", e.getMessage());
			return false;
		} catch (Exception e) {
			log.error("Invalid token: {}", e.getMessage());
			return false;
		}
	}

	private Claims parseClaims(String token) {
		return Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(token)
			.getPayload();
	}

	public String getEmailFromToken(String token) {
		return parseClaims(token).get("email", String.class);
	}

	public Role getRoleFromToken(String token) {
		String roleStr = parseClaims(token).get("role", String.class);
		return Role.valueOf(roleStr);
	}
}
