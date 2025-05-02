package com.its.argument_resolver.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.its.argument_resolver.annotation.UserInfo;
import com.its.argument_resolver.dto.UserDto;
import com.its.argument_resolver.util.JwtProvider;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserArgumentResolverV2 implements HandlerMethodArgumentResolver {

	private final JwtProvider jwtProvider;

	private static final String AUTHORIZATION_HEADER = "Authorization";

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(UserInfo.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		String token = request.getHeader(AUTHORIZATION_HEADER);
		jwtProvider.isValidToken(token);

		Long userId = jwtProvider.getUserIdFromToken(token);
		String ip = request.getRemoteAddr();
		return new UserDto(userId, ip);
	}
}
