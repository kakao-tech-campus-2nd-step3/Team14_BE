package com.ordertogether.team14_be.member.application.service;

import com.ordertogether.team14_be.member.persistence.MemberRepository;
import com.ordertogether.team14_be.member.persistence.entity.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	@Transactional
	public void findOrCreateMember(String email) {
		Member member =
				memberRepository
						.findByEmail(email)
						.orElseGet(
								() -> {
									Member newMember = Member.from(email);
									return memberRepository.saveAndFlush(newMember);
								});
	}

	public Long getMemberId(String email) {
		Member member = memberRepository.findByEmail(email).get();
		return member.getId();
	}
}
