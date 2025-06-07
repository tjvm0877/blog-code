package hello.its.oauth.domain.member.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import hello.its.oauth.global.security.constants.OAuthProvider;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(value = EnumType.STRING)
	private Role role;

	private String nickname;

	@Enumerated(value = EnumType.STRING)
	private OAuthProvider provider;

	private String email;

	private String identifier;

	private String name;

	private String profileImage;

	@CreationTimestamp
	@Column(name = "create_at", nullable = false, updatable = false)
	private LocalDateTime createAt;

	@UpdateTimestamp
	@Column(name = "update_at", nullable = false)
	private LocalDateTime updateAt;

	@Builder
	public Member(Role role, String nickname, OAuthProvider provider, String email, String identifier, String name,
		String profileImage) {
		this.role = role;
		this.nickname = nickname;
		this.provider = provider;
		this.email = email;
		this.identifier = identifier;
		this.name = name;
		this.profileImage = profileImage;
	}

	public void registerMember(String nickname) {
		this.nickname = nickname;
		this.role = Role.MEMBER;
	}
}
