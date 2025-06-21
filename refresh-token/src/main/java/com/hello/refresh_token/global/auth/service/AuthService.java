package com.hello.refresh_token.global.auth.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.hello.refresh_token.global.auth.dto.ExchangeRequest;
import com.hello.refresh_token.global.auth.dto.ExchangeResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final RefreshTokenService refreshTokenService;
	private final JwtProvider jwtProvider;

	public ExchangeResponse exchange(ExchangeRequest request) {
		if (!jwtProvider.isValidateToken(request.refreshToken())) {
			throw new IllegalArgumentException();
		}

		UUID uuid = jwtProvider.getUuidFromToken(request.refreshToken());
		String savedRefreshToken = refreshTokenService.get(uuid.toString());

		if (savedRefreshToken == null) {
			throw new IllegalArgumentException("Refresh token expired or invalid");
		}

		if (!savedRefreshToken.equals(request.refreshToken())) {
			throw new IllegalArgumentException();
		}

		String accessToken = jwtProvider.generateAccessToken(uuid);
		String refreshToken = jwtProvider.generateRefreshToken(uuid);
		refreshTokenService.save(uuid.toString(), refreshToken);

		return new ExchangeResponse(accessToken, refreshToken);
	}
}
