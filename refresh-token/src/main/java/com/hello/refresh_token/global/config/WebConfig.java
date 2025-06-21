package com.hello.refresh_token.global.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.hello.refresh_token.global.auth.AuthArgumentResolver;
import com.hello.refresh_token.global.auth.service.JwtProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

	private final JwtProvider jwtProvider;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(authArgumentResolver());
	}

	@Bean
	public AuthArgumentResolver authArgumentResolver() {
		return new AuthArgumentResolver(jwtProvider);
	}
}
