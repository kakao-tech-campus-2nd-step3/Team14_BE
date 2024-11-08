package com.ordertogether.team14_be.auth.application.service;

import com.ordertogether.team14_be.auth.persistence.JwtUtil;
import com.ordertogether.team14_be.member.application.service.MemberService;
import com.ordertogether.team14_be.member.persistence.entity.Member;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
	private final MemberService memberService;
	private final JwtUtil jwtUtil;

	public AuthService(MemberService memberService, JwtUtil jwtUtil) {
		this.memberService = memberService;
		this.jwtUtil = jwtUtil;
	}

	public String register(String email, String deliveryName, String phoneNumber) {
		Member member = new Member(email, deliveryName, phoneNumber);
		memberService.registerMember(member);
		Long memberId = memberService.getMemberId(email);
		String serviceToken = jwtUtil.generateToken(memberId);
		return serviceToken;
	}

	public String getServiceToken(String email) {
		return jwtUtil.generateToken(memberService.getMemberId(email));
	}
}
