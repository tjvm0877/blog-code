package com.its.refresh_token.util;

import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtProvider {

	@Value("${jwt.secret-key}")
	private String secretKey;

	private SecretKey signKey;

	@PostConstruct
	private void createSignKey() {
		byte[] keyBytes = Base64.getDecoder().decode(secretKey);
		this.signKey = Keys.hmacShaKeyFor(keyBytes);
	}

	public String generateAccessToken(UUID uuid, long duration) {
		return Jwts.builder()
			.subject(uuid.toString())
			.issuedAt(new Date())
			.expiration(new Date(System.currentTimeMillis() + duration))
			.signWith(signKey)
			.compact();
	}

	public String generateRefreshToken(UUID uuid, long duration) {
		return Jwts.builder()
			.subject(uuid.toString())
			.issuedAt(new Date())
			.expiration(new Date(System.currentTimeMillis() + duration))
			.signWith(signKey)
			.compact();
	}

	public UUID getUuidFromToken(String token) {
		Claims claims = Jwts.parser()
			.verifyWith(signKey)
			.build()
			.parseSignedClaims(token)
			.getPayload();
		return UUID.fromString(claims.getSubject());
	}

	public boolean isValidateToken(String token) {
		try {
			Jwts.parser()
				.verifyWith(signKey)
				.build()
				.parseSignedClaims(token);
			return true;
		} catch (ExpiredJwtException ex) {
			log.warn("Token expired: {}", ex.getMessage());
		} catch (UnsupportedJwtException | MalformedJwtException | SignatureException ex) {
			log.warn("Invalid token structure: {}", ex.getMessage());
		} catch (IllegalArgumentException ex) {
			log.error("Empty or null token: {}", ex.getMessage());
		}

		return false;
	}
}
