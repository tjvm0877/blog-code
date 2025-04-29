package com.its.auth_with_annotation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.its.auth_with_annotation.dto.MemberInfo;
import com.its.auth_with_annotation.service.MemberService;
import com.its.auth_with_annotation.util.JwtProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberControllerV1 {

	private final MemberService memberService;
	private final JwtProvider jwtProvider;

	// @RequestHeader를 통해서 헤더에 들어있는 JWT를 받는다.
	@GetMapping
	public ResponseEntity<?> getCurrentUser(@RequestHeader("Authorization") String jwt) {
		// 임시로 Controller에서 JWT를 검증한다.
		if (!jwtProvider.isValidToken(jwt)) {
			throw new RuntimeException();
		}
		// JWT에서 회원 정보를 추출
		Long memberId = jwtProvider.getMemberIdFromToken(jwt);

		MemberInfo response = memberService.getMember(memberId);
		return ResponseEntity.ok(response);
	}
}
