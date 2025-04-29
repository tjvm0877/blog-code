package com.its.auth_with_annotation.service;

import org.springframework.stereotype.Service;

import com.its.auth_with_annotation.domain.Member;
import com.its.auth_with_annotation.dto.MemberInfo;
import com.its.auth_with_annotation.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	public MemberInfo getMember(Long memberId) {
		Member member = memberRepository.findById(memberId).orElseThrow(RuntimeException::new);
		return MemberInfo.from(member);
	}
}
