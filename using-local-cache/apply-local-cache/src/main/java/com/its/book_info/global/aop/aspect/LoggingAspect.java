package com.its.book_info.global.aop.aspect;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {

	private final CacheManager cacheManager;

	@After("@annotation(com.its.book_info.global.aop.annotation.CacheLogging)")
	public void CacheLogging() {
		for (String cacheName : cacheManager.getCacheNames()) {
			Cache cache = cacheManager.getCache(cacheName);
			if (cache != null && cache.getNativeCache() instanceof com.github.benmanes.caffeine.cache.Cache) {
				com.github.benmanes.caffeine.cache.Cache nativeCache =
					(com.github.benmanes.caffeine.cache.Cache)cache.getNativeCache();
				log.info("{}: {}", cacheName, nativeCache.stats());
			}
		}
	}

	@Around("@annotation(com.its.book_info.global.aop.annotation.LogExecutionTime)")
	public Object printCurrentTime(ProceedingJoinPoint pointcut) throws Throwable {
		long startTime = System.currentTimeMillis();

		Object result = pointcut.proceed();

		long endTime = System.currentTimeMillis();
		long duration = endTime - startTime;
		String methodName = pointcut.getSignature().toShortString();
		log.info("{}: {}ms", methodName, duration);
		return result;
	}

}
