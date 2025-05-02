package com.its.argument_resolver.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.its.argument_resolver.dto.UserDto;
import com.its.argument_resolver.util.JwtProvider;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserArgumentResolverV1 implements HandlerMethodArgumentResolver {

	private final JwtProvider jwtProvider;

	private static final String AUTHORIZATION_HEADER = "Authorization";

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		// 컨트롤러 메서드에 UserDto 타입이 있는지 확인
		// True를 반환할 시 resolveArgument() 실행
		return parameter.getParameterType().equals(UserDto.class);
	}

	// 컨트롤러에서 반복된 HTTP 헤더로부터 JWT관련 로직, 클라이언트 IP 가져오기 로직을 넣어준다.
	// 최종적으로 UserDto 를 생성해서 반환
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
