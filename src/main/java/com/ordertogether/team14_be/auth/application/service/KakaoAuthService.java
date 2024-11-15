package com.ordertogether.team14_be.auth.application.service;

import com.ordertogether.team14_be.auth.application.dto.KakaoUserInfo;
import com.ordertogether.team14_be.auth.persistence.JwtUtil;
import com.ordertogether.team14_be.auth.presentation.KakaoClient;
import com.ordertogether.team14_be.member.application.service.MemberService;
import com.ordertogether.team14_be.member.persistence.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

@RequiredArgsConstructor
@Service
public class KakaoAuthService {
	private final KakaoClient kakaoClient;
	private final MemberService memberService;
	private final JwtUtil jwtUtil;
	private static final String LOGIN_PLATFORM = "KAKAO";
	private final AuthService authService;

	public String getKakaoUserEmail(String authorizationCode) {
		try {
			String kakaoToken = kakaoClient.getAccessToken(authorizationCode);
			KakaoUserInfo kakaoUserInfo = kakaoClient.getUserInfo((kakaoToken));
			String userKakaoEmail = kakaoUserInfo.kakaoAccount().email();
			return userKakaoEmail;
		} catch (RestClientException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	public String register(String email, String deliveryName, String phoneNumber) {
		Member member = new Member(email, 0, phoneNumber, deliveryName, LOGIN_PLATFORM);
		authService.validMember(email, LOGIN_PLATFORM);
		memberService.registerMember(member);
		Long memberId = memberService.getMemberId(email);
		String serviceToken = jwtUtil.generateToken(memberId);
		return serviceToken;
	}
}
