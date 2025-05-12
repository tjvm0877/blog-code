package com.its.book_info.global.config;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.its.book_info.global.cache.CacheType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableCaching
@RequiredArgsConstructor
public class CacheConfig {

	private final RedisConnectionFactory redisConnectionFactory;

	@Bean
	public CacheManager cacheManager() {
		// Redis 캐시 기본 설정
		RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
			// 캐시 키를 직렬화하는 방식을 StringRedisSerializer로 설정
			.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(
				new StringRedisSerializer()
			))
			// 캐시 값을 직렬화하는 방식을 GenericJackson2JsonRedisSerializer로 설정
			// 이를 통해 객체를 JSON 형태로 저장
			.serializeValuesWith(
				RedisSerializationContext.SerializationPair.fromSerializer(
					new GenericJackson2JsonRedisSerializer()
				)
			)
			// null 값은 캐싱하지 않도록 설정
			.disableCachingNullValues();

		// 각 캐시 타입별 설정을 저장할 Map을 생성
		Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();

		for (CacheType cacheType : CacheType.values()) {
			// 각 캐시 타입별로 만료 시간을 설정하여 캐시 설정에 추가
			cacheConfigurations.put(
				cacheType.getCacheName(), // 캐시 이름을 키로 사용
				defaultConfig.entryTtl(Duration.ofSeconds(cacheType.getExpireAfterWrite())) // 만료 시간 설정
			);

			log.info("Redis 캐시 설정: {}, TTL: {} 초",
				cacheType.getCacheName(),
				cacheType.getExpireAfterWrite());
		}

		// Redis 캐시 매니저 생성 및 설정
		RedisCacheManager cacheManager = RedisCacheManager.builder(redisConnectionFactory)
			.cacheDefaults(defaultConfig) // 기본 캐시 설정 적용
			.withInitialCacheConfigurations(cacheConfigurations) // 캐시 타입별 설정
			.transactionAware() // 트랜잭션 인식 활성화
			.build();

		// 생성된 캐시 매니저를 반환
		return cacheManager;
	}
}

