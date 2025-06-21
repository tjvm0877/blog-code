package com.hello.refresh_token.global.auth.service;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

	private final RedisTemplate<String, Object> redisTemplate;
	private static final String REFRESH_TOKEN_PREFIX = "refresh_token";

	@Value("${jwt.refresh-token-expiration}")
	private long REFRESH_TOKEN_EXPIRATION;

	public void save(String uuid, String token) {
		String key = REFRESH_TOKEN_PREFIX + uuid;
		redisTemplate.opsForValue().set(key, token, Duration.ofMillis(REFRESH_TOKEN_EXPIRATION));
	}

	public String get(String uuid) {
		String key = REFRESH_TOKEN_PREFIX + uuid;
		return (String)redisTemplate.opsForValue().getAndDelete(key);
	}

	public void delete(String uuid) {
		String key = REFRESH_TOKEN_PREFIX + uuid;
		redisTemplate.delete(key);
	}
}
