package com.hello.refresh_token.global.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hello.refresh_token.global.auth.dto.ExchangeRequest;
import com.hello.refresh_token.global.auth.dto.ExchangeResponse;
import com.hello.refresh_token.global.auth.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

	private final AuthService authService;

	@PostMapping("/exchange")
	public ResponseEntity<?> exchangeToken(@RequestBody ExchangeRequest request) {
		ExchangeResponse response = authService.exchange(request);
		return ResponseEntity.ok(response);
	}
}
