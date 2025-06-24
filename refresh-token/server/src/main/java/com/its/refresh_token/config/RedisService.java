package com.its.refresh_token.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisService {
	@Value("${spring.redis.host}")
	private String host;

	@Value("${spring.redis.port}")
	private int port;

	@Value("${spring.redis.password}")
	private String password;

	@Bean // Redis 연결을 위한 ConnectionFactory 빈 등록
	public RedisConnectionFactory redisConnectionFactory() {
		// Redis 단일 서버 설정(host, port)
		RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);
		config.setPassword(password);

		// Lettuce 클라이언트로 RedisConnectionFactory 생성 및 반환
		return new LettuceConnectionFactory(config);
	}

	@Bean // RedisTemplate을 스프링 빈으로 등록
	public RedisTemplate<String, Object> redisTemplate() {
		// String 타입의 키와 Object 타입의 값을 사용하는 RedisTemplate 객체 생성
		RedisTemplate<String, Object> template = new RedisTemplate<>();

		// 위에서 생성한 Redis 연결 팩토리 설정
		template.setConnectionFactory(redisConnectionFactory());

		// 키를 직렬화할 때 문자열 형식으로 변환하는 StringRedisSerializer 설정
		template.setKeySerializer(new StringRedisSerializer());

		// 값을 직렬화할 때 JSON 형식으로 변환하는 GenericJackson2JsonRedisSerializer 설정
		template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

		// 구성이 완료된 RedisTemplate 반환
		return template;
	}
}
