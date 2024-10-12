package com.ordertogether.team14_be.auth.application.service;

import com.ordertogether.team14_be.auth.JwtUtil;
import com.ordertogether.team14_be.auth.application.dto.KakaoUserInfo;
import com.ordertogether.team14_be.auth.presentation.KakaoClient;
import com.ordertogether.team14_be.member.application.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

	private final KakaoClient kakaoClient;
	private final MemberService memberService;
	private static JwtUtil jwtUtil;

	public String kakaoLogin(String authorizationCode) {
		String kakaoToken = kakaoClient.getAccessToken(authorizationCode); // 인가코드로부터 카카오토큰 발급
		KakaoUserInfo kakaoUserInfo = kakaoClient.getUserInfo((kakaoToken));
		String userKakaoEmail = kakaoUserInfo.kakaoAccount().email();
		memberService.findOrCreateMember(userKakaoEmail);
		String serviceToken = jwtUtil.generateToken(memberService.getMemberId(userKakaoEmail));

		return serviceToken;
	}
}
