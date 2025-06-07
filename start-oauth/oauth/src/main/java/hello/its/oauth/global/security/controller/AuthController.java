package hello.its.oauth.global.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hello.its.oauth.global.security.dto.TokenRequest;
import hello.its.oauth.global.security.dto.TokenResponse;
import hello.its.oauth.global.security.service.AuthService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping
	public ResponseEntity<?> exchangeAuthToken(@RequestBody TokenRequest request) {
		TokenResponse response = authService.validateAndGenerateToken(request);
		return ResponseEntity.ok(response);
	}

}
