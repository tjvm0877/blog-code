package com.its.refresh_token.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.its.refresh_token.dto.TokenRequest;
import com.its.refresh_token.dto.TokenResponse;
import com.its.refresh_token.util.JwtProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenService {

	private final RefreshTokenService refreshTokenService;
	private final JwtProvider jwtProvider;

	// 토큰 발급
	public TokenResponse issueToken(TokenRequest request) {

		// UUID 발급
		UUID uuid = UUID.randomUUID();

		String accessToken = jwtProvider.generateAccessToken(uuid, request.accessTokenDuration());
		String refreshToken = jwtProvider.generateRefreshToken(uuid, request.refreshTokenDuration());
		refreshTokenService.save(uuid, refreshToken, request.refreshTokenDuration());

		return TokenResponse.of(accessToken, refreshToken);
	}

	// 리프레시 토큰 발급
	public TokenResponse reissueToken(String token, TokenRequest request) {
		UUID uuidFromToken = jwtProvider.getUuidFromToken(token);

		String savedToken = refreshTokenService.getAndDelete(uuidFromToken);
		if (!token.equals(savedToken)) {
			throw new IllegalArgumentException();
		}

		String accessToken = jwtProvider.generateAccessToken(uuidFromToken, request.accessTokenDuration());
		String refreshToken = jwtProvider.generateRefreshToken(uuidFromToken, request.refreshTokenDuration());
		refreshTokenService.save(uuidFromToken, refreshToken, request.refreshTokenDuration());

		return TokenResponse.of(accessToken, refreshToken);
	}
}
