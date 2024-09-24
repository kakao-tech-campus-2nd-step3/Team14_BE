package com.ordertogether.team14_be.memebr.application.service;

import com.ordertogether.team14_be.memebr.persistence.MemberRepository;
import com.ordertogether.team14_be.memebr.persistence.entity.Member;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

	private final MemberRepository memberRepository;

	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public void findOrCreateMember(String email) {
		Member member =
				memberRepository
						.findByEmail(email)
						.orElseGet(
								() -> {
									Member newMember = Member.createMember(email);
									return memberRepository.saveAndFlush(newMember);
								});
	}
}
