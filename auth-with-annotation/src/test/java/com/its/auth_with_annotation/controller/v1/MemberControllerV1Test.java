package com.its.auth_with_annotation.controller.v1;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.its.auth_with_annotation.dto.SignInRequest;
import com.its.auth_with_annotation.dto.SignUpRequest;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerV1Test {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@Test()
	@DisplayName("회원가입 - 로그인 - 회원 정보 불러오기")
	public void test1() throws Exception {
		String email = "test@gmail.com";
		String password = "1234";

		// 회원가입
		SignUpRequest request1 = new SignUpRequest(email, password);
		mockMvc.perform(post("/sign-up")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(request1))
		).andExpect(status().isOk());

		// 로그인
		SignInRequest request2 = new SignInRequest(email, password);
		MvcResult result = mockMvc.perform(post("/sign-in")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request2))
			).andExpect(status().isOk())
			.andDo(print())
			.andReturn();
		String responseBody = result.getResponse().getContentAsString();
		String accessToken = objectMapper.readTree(responseBody).get("accessToken").asText();

		// 회원 정보 조회
		mockMvc.perform(get("/members")
			.header("Authorization", "Bearer " + accessToken)
		).andExpect(status().isOk()).andDo(print());
	}
}