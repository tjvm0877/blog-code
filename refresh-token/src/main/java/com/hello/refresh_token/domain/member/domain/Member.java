package com.hello.refresh_token.domain.member.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "uuid", nullable = false, unique = true)
	private UUID uuid;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "password", nullable = false)
	private String password;

	@CreationTimestamp
	@Column(name = "create_at", nullable = false, updatable = false)
	private LocalDateTime createAt;

	@UpdateTimestamp
	@Column(name = "update_at", nullable = false)
	private LocalDateTime updateAt;

	public Member(String email, String password) {
		this.uuid = UUID.randomUUID();
		this.email = email;
		this.password = password;
	}
}
