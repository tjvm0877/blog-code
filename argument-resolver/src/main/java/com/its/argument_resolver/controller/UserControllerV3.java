package com.its.argument_resolver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.its.argument_resolver.annotation.UserInfo;
import com.its.argument_resolver.dto.UserDto;

@RestController
@RequestMapping("/v3")
public class UserControllerV3 {

	@PostMapping("/users")
	public ResponseEntity<Void> create1(@UserInfo UserDto userDto) {

		// userDto.id, userDto.ip 를 사용하여 요청 처리
		// ...

		return ResponseEntity.ok().build();
	}
}
