package com.ordertogether.team14_be.auth.application.service;

import com.ordertogether.team14_be.auth.persistence.JwtUtil;
import com.ordertogether.team14_be.auth.persistence.exception.AlreadyExistMember;
import com.ordertogether.team14_be.member.application.service.MemberService;
import com.ordertogether.team14_be.member.persistence.entity.Member;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
	private final MemberService memberService;
	private final JwtUtil jwtUtil;

	public AuthService(MemberService memberService, JwtUtil jwtUtil) {
		this.memberService = memberService;
		this.jwtUtil = jwtUtil;
	}

	public String getServiceToken(String email) {
		return jwtUtil.generateToken(memberService.getMemberId(email));
	}

	public void validMember(String email, String platform) {
		Optional<Member> isAlreadyMember = memberService.findMemberByEmail(email);
		if (isAlreadyMember.isPresent() && isAlreadyMember.get().getPlatform().equals(platform)) {
			throw new AlreadyExistMember();
		}
	}
}
