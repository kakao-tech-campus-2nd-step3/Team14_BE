package com.ordertogether.team14_be.member.application.service;

import com.ordertogether.team14_be.auth.JwtUtil;
import com.ordertogether.team14_be.member.application.dto.MemberInfoResponse;
import com.ordertogether.team14_be.member.application.exception.NotFoundMember;
import com.ordertogether.team14_be.member.persistence.MemberRepository;
import com.ordertogether.team14_be.member.persistence.entity.Member;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final JwtUtil jwtUtil;

	@Transactional(readOnly = true)
	public Long getMemberId(String email) {
		return memberRepository
				.findByEmail(email)
				.map(Member::getId)
				.orElseThrow(() -> new NoSuchElementException("Member with email " + email + " not found"));
	}

	@Transactional(readOnly = true)
	public MemberInfoResponse findMemberInfo(Long memberId) {
		Member member = findMember(memberId);

		return MemberInfoResponse.builder()
				.deliveryName(member.getDeliveryName())
				.phoneNumber(member.getPhoneNumber())
				.point(member.getPoint())
				.build();
	}

	@Transactional
	public MemberInfoResponse modifyMember(Long memberId, String deliveryName, String phoneNumber) {
		Member member = findMember(memberId);
		member.modifyMemberInfo(deliveryName, phoneNumber);
		memberRepository.saveAndFlush(member);
		return MemberInfoResponse.builder()
				.deliveryName(member.getDeliveryName())
				.phoneNumber(member.getPhoneNumber())
				.point(member.getPoint())
				.build();
	}

	@Transactional(readOnly = true)
	public Member findMember(Long memberId) {
		Member member = memberRepository.findById(memberId).orElseThrow(() -> new NotFoundMember());
		return member;
	}

	@Transactional
	public void deleteMember(Long memberId) {
		memberRepository.deleteById(memberId);
	}

	@Transactional(readOnly = true)
	public Optional<Member> findMemberByEmail(String email) {
		return memberRepository.findByEmail(email);
	}

	@Transactional
	public void registerMember(Member member) {
		memberRepository.saveAndFlush(member);
	}
}
