package com.hello.refresh_token.global.auth;

import java.util.UUID;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.hello.refresh_token.global.auth.service.JwtProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthArgumentResolver implements HandlerMethodArgumentResolver {

	private final JwtProvider jwtProvider;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(Authenticated.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		String authHeader = webRequest.getHeader("Authorization");
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			// TODO: 커스텀 예외 필요
			throw new IllegalArgumentException("Missing or invalid Authorization header");
		}

		String token = authHeader.substring(7);

		if (!jwtProvider.isValidateToken(token)) {
			throw new IllegalArgumentException("Invalid token");
		}

		return jwtProvider.getUuidFromToken(token);
	}
}
