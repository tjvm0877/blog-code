package hello.its.oauth.domain.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hello.its.oauth.domain.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByIdentifier(String identifier);

	Optional<Member> findByEmail(String email);
}
