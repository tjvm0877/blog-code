package com.its.argument_resolver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.its.argument_resolver.dto.UserDto;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/v2")
public class UserControllerV2 {

	@PostMapping("/users")
	public ResponseEntity<Void> create1(UserDto userDto) { // UserArgumentResolver에서 UserDto를 생성해서 넘겨준다.

		// userDto.id, userDto.ip 를 사용하여 요청 처리
		// ...

		return ResponseEntity.ok().build();
	}
}
