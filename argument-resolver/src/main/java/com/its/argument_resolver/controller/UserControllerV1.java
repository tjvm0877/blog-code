package com.its.argument_resolver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.its.argument_resolver.util.JwtProvider;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class UserControllerV1 {

	private final JwtProvider jwtProvider;

	@PostMapping("/users")
	public ResponseEntity<Void> create1(HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		jwtProvider.isValidToken(token);

		Long userId = jwtProvider.getUserIdFromToken(token);
		String ipAddress = request.getRemoteAddr();

		// userId와 ipAddress를 사용하여 요청 처리

		return ResponseEntity.ok().build();
	}
}
