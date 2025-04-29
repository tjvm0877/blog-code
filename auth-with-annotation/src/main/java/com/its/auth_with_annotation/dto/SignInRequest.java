package com.its.auth_with_annotation.dto;

public record SignInRequest(
	String email,
	String password
) {
}
