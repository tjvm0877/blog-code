package com.its.refresh_token.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.its.refresh_token.annotation.ValidateJWT;
import com.its.refresh_token.exception.InvalidTokenException;
import com.its.refresh_token.exception.TokenNotFoundException;
import com.its.refresh_token.util.JwtProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TokenArgumentResolver implements HandlerMethodArgumentResolver {

	private final JwtProvider jwtProvider;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(ValidateJWT.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

		String authHeader = webRequest.getHeader("Authorization");
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			throw new TokenNotFoundException();
		}

		String token = authHeader.substring(7);

		if (!jwtProvider.isValidateToken(token)) {
			throw new InvalidTokenException();
		}

		return jwtProvider.getUuidFromToken(token);
	}
}
