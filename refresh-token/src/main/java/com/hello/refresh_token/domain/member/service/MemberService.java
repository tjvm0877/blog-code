package com.hello.refresh_token.domain.member.service;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hello.refresh_token.domain.member.domain.Member;
import com.hello.refresh_token.domain.member.dto.MemberCreateRequest;
import com.hello.refresh_token.domain.member.dto.MemberCreateResponse;
import com.hello.refresh_token.domain.member.dto.MemberInfoResponse;
import com.hello.refresh_token.domain.member.dto.MemberLoginRequest;
import com.hello.refresh_token.domain.member.dto.MemberLoginResponse;
import com.hello.refresh_token.domain.member.repository.MemberRepository;
import com.hello.refresh_token.global.auth.Authenticated;
import com.hello.refresh_token.global.auth.service.JwtProvider;
import com.hello.refresh_token.global.auth.service.RefreshTokenService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final JwtProvider jwtProvider;
	private final PasswordEncoder passwordEncoder;
	private final RefreshTokenService refreshTokenService;
	private final MemberRepository memberRepository;

	@Transactional
	public MemberCreateResponse createMember(MemberCreateRequest request) {
		Member member = new Member(
			request.email(),
			passwordEncoder.encode(request.password())
		);
		memberRepository.save(member);

		String accessToken = jwtProvider.generateAccessToken(member.getUuid());
		String refreshToken = jwtProvider.generateRefreshToken(member.getUuid());
		refreshTokenService.save(member.getUuid().toString(), refreshToken);
		return new MemberCreateResponse(accessToken, refreshToken);
	}

	@Transactional(readOnly = true)
	public MemberLoginResponse login(MemberLoginRequest request) {
		Member member = memberRepository.findByEmail(request.email()).orElseThrow(IllegalArgumentException::new);
		if (!passwordEncoder.matches(request.password(), member.getPassword())) {
			throw new IllegalArgumentException();
		}
		String accessToken = jwtProvider.generateAccessToken(member.getUuid());
		String refreshToken = jwtProvider.generateRefreshToken(member.getUuid());
		refreshTokenService.save(member.getUuid().toString(), refreshToken);
		return new MemberLoginResponse(accessToken, refreshToken);
	}

	@Transactional(readOnly = true)
	public MemberInfoResponse getMemberInfo(UUID memberUuid) {
		Member member = memberRepository.findByUuid(memberUuid).orElseThrow(IllegalArgumentException::new);
		return MemberInfoResponse.from(member);
	}
}
