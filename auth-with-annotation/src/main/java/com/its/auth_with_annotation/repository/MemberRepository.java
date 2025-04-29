package com.its.auth_with_annotation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.its.auth_with_annotation.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByEmail(String email);
}
