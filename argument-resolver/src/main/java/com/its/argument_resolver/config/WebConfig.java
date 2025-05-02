package com.its.argument_resolver.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.its.argument_resolver.resolver.UserArgumentResolverV1;
import com.its.argument_resolver.util.JwtProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

	private final JwtProvider jwtProvider;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(userArgumentResolver());
	}

	@Bean
	public UserArgumentResolverV1 userArgumentResolver() {
		return new UserArgumentResolverV1(jwtProvider);
	}
}
