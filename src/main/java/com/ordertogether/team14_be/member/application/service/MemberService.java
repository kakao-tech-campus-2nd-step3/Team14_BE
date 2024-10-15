package com.ordertogether.team14_be.member.application.service;

import com.ordertogether.team14_be.auth.JwtUtil;
import com.ordertogether.team14_be.member.application.dto.MemberInfoRequest;
import com.ordertogether.team14_be.member.application.dto.MemberInfoResponse;
import com.ordertogether.team14_be.member.application.exception.NotFoundMember;
import com.ordertogether.team14_be.member.persistence.MemberRepository;
import com.ordertogether.team14_be.member.persistence.entity.Member;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final JwtUtil jwtUtil;

	@Transactional
	public void findOrCreateMember(String email) {
		Member member = memberRepository.findByEmail(email).get();
		String token = jwtUtil.generateToken(member.getId());

		//						.orElseGet(
		//								() -> {
		//									Member newMember = Member.from(email);
		//									return memberRepository.saveAndFlush(newMember);
		//								});
	}

	@Transactional
	public Long getMemberId(String email) {
		Member member = memberRepository.findByEmail(email).get();
		return member.getId();
	}

	public MemberInfoResponse findMemberInfo(Long memberId) {
		Member member = findMember(memberId);

		return MemberInfoResponse.builder()
				.deliveryName(member.getDeliveryName())
				.phoneNumber(member.getPhoneNumber())
				.point(member.getPoint())
				.build();
	}

	public MemberInfoResponse modifyMember(Long memberId, MemberInfoRequest memberInfoRequest) {
		Member member = findMember(memberId);
		member.modifyMemberInfo(memberInfoRequest.deliveryName(), memberInfoRequest.phoneNumber());
		return MemberInfoResponse.builder()
				.deliveryName(member.getDeliveryName())
				.phoneNumber(member.getPhoneNumber())
				.point(member.getPoint())
				.build();
	}

	@Transactional
	public Member findMember(Long memberId) {
		Member member = memberRepository.findById(memberId).orElseThrow(() -> new NotFoundMember());
		return member;
	}

	@Transactional
	public void deleteMember(Long memberId) {
		memberRepository.deleteById(memberId);
	}

	public void registerMember() {}

	public Optional<Member> findMemberByEmail(String email) {
		return memberRepository.findByEmail(email);
	}

	public void registerMember(Member member) {
		memberRepository.saveAndFlush(member);
	}
}
