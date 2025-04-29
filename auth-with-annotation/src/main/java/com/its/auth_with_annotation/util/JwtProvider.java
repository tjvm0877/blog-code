package com.its.auth_with_annotation.util;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
	private final Long ACCESS_TOKEN_EXPIRATION_TIME;

	public JwtProvider(@Value("${jwt.secret}") String SECRET_KEY, @Value("${jwt.expiration}") Long EXPIRATION_TIME) {
		if (SECRET_KEY == null || SECRET_KEY.isEmpty()) {
			throw new IllegalArgumentException("JWT secret key must not be null or empty");
		}
		if (EXPIRATION_TIME == null || EXPIRATION_TIME <= 0) {
			throw new IllegalArgumentException("JWT expiration time must be a positive value");
		}
		this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
		this.ACCESS_TOKEN_EXPIRATION_TIME = EXPIRATION_TIME;
	}

	public String generateAccessToken(Long id) {
		return Jwts.builder()
			.claim("id", id)
			.issuedAt(new Date(System.currentTimeMillis()))
			.expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME))
			.signWith(secretKey)
			.compact();
	}

	public Long getMemberIdFromToken(String token) {
		return parseClaims(token).get("id", Long.class);
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
		String jwt = extractToken(token);
		return Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(jwt)
			.getPayload();
	}

	private String extractToken(String token) {
		if (!token.startsWith(BEARER_PREFIX)) {
			throw new IllegalArgumentException("Invalid Bearer prefix in token");
		}
		return token.substring(BEARER_PREFIX.length());
	}
}
