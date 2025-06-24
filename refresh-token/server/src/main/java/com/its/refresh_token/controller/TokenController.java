package com.its.refresh_token.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/token")
public class TokenController {

	@PostMapping
	public void issueToken() {
		
	}

	@PostMapping("/refresh")
	public void refreshToken() {

	}
}
