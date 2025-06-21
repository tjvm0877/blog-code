package com.hello.refresh_token.domain.member.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hello.refresh_token.domain.member.dto.MemberCreateRequest;
import com.hello.refresh_token.domain.member.dto.MemberCreateResponse;
import com.hello.refresh_token.domain.member.dto.MemberInfoResponse;
import com.hello.refresh_token.domain.member.dto.MemberLoginRequest;
import com.hello.refresh_token.domain.member.dto.MemberLoginResponse;
import com.hello.refresh_token.domain.member.service.MemberService;
import com.hello.refresh_token.global.auth.Authenticated;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody MemberCreateRequest request) {
		MemberCreateResponse response = memberService.createMember(request);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody MemberLoginRequest request) {
		MemberLoginResponse response = memberService.login(request);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/info")
	public ResponseEntity<?> getInfo(@Authenticated UUID memberUuid) {
		MemberInfoResponse response = memberService.getMemberInfo(memberUuid);
		return ResponseEntity.ok(response);
	}
}
