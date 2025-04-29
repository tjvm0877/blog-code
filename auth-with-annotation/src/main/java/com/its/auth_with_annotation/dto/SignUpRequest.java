package com.its.auth_with_annotation.dto;

public record SignUpRequest(
	String email,
	String password
) {
}
