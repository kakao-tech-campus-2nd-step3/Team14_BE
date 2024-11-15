package com.ordertogether.team14_be.auth.application.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.ordertogether.team14_be.auth.persistence.exception.AlreadyExistMember;
import com.ordertogether.team14_be.member.persistence.MemberRepository;
import com.ordertogether.team14_be.member.persistence.entity.Member;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class AuthServiceTest {

	private final AuthService authService;
	private final MemberRepository memberRepository;

	@Autowired
	public AuthServiceTest(AuthService authService, MemberRepository memberRepository) {
		this.authService = authService;
		this.memberRepository = memberRepository;
	}

	@Test
	void validMemberTest() {
		// given
		String email = "a1007panda@gmail.com";
		String platform = "KAKAO";
		Member member = new Member(email, 0, "010-7731-3160", "서영우", platform);
		memberRepository.saveAndFlush(member);

		// when
		assertThatThrownBy(() -> authService.validMember(email, platform))
				.isInstanceOf(AlreadyExistMember.class)
				.hasMessageContaining("이미 회원이 존재합니다.");
	}
}
