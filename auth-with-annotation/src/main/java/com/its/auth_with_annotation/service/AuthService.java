package com.its.auth_with_annotation.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.its.auth_with_annotation.util.JwtProvider;
import com.its.auth_with_annotation.domain.Member;
import com.its.auth_with_annotation.repository.MemberRepository;
import com.its.auth_with_annotation.dto.SignInRequest;
import com.its.auth_with_annotation.dto.SignInResponse;
import com.its.auth_with_annotation.dto.SignUpRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final MemberRepository memberRepository;
	private final JwtProvider jwtProvider;

	@Transactional
	public void register(SignUpRequest request) {
		Member member = new Member(request.email(), request.password());
		memberRepository.save(member);
	}

	public SignInResponse signIn(SignInRequest request) {
		Member member = memberRepository.findByEmail(request.email()).orElseThrow(RuntimeException::new);
		if (!member.getPassword().equals(request.password())) {
			throw new RuntimeException("로그인 실패");
		}
		return new SignInResponse(jwtProvider.generateAccessToken(member.getId()));
	}
}
