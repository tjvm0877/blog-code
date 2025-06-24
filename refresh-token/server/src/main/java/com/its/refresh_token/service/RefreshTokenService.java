package com.its.refresh_token.service;

import java.time.Duration;
import java.util.UUID;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

	private final RedisTemplate<String, Object> redisTemplate;
	private static final String REFRESH_TOKEN_PREFIX = "refresh_token:";

	public void save(UUID uuid, String token, long duration) {
		String key = buildKey(uuid);
		redisTemplate.opsForValue().set(key, token, Duration.ofMillis(duration));
	}

	public String getAndDelete(UUID uuid) {
		String key = buildKey(uuid);
		return (String)redisTemplate.opsForValue().getAndDelete(key);
	}

	private String buildKey(UUID uuid) {
		return REFRESH_TOKEN_PREFIX + uuid.toString();
	}
}
