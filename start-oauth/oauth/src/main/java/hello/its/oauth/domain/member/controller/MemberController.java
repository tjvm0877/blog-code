package hello.its.oauth.domain.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hello.its.oauth.domain.member.dto.SignUpResponse;
import hello.its.oauth.domain.member.dto.MemberInfoResponse;
import hello.its.oauth.domain.member.dto.SignUpRequest;
import hello.its.oauth.domain.member.service.MemberService;
import hello.its.oauth.global.security.authentication.JwtAuthenticationToken;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@GetMapping
	public ResponseEntity<?> getMemberInfo(JwtAuthenticationToken authentication) {
		String email = authentication.getEmail();
		MemberInfoResponse response = memberService.getMemberInfoByEmail(email);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/sign-up")
	public ResponseEntity<?> signUp(@RequestBody SignUpRequest request) {
		SignUpResponse response = memberService.register(request);
		return ResponseEntity.ok(response);
	}
}
