package com.ordertogether.team14_be.auth.application.service;

import com.ordertogether.team14_be.auth.JwtUtil;
import com.ordertogether.team14_be.auth.application.dto.KakaoUserInfo;
import com.ordertogether.team14_be.auth.presentation.KakaoClient;
import com.ordertogether.team14_be.common.web.response.ApiResponse;
import com.ordertogether.team14_be.member.application.service.MemberService;
import com.ordertogether.team14_be.member.persistence.entity.Member;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

	private final KakaoClient kakaoClient;
	private final MemberService memberService;
	private final JwtUtil jwtUtil;

	@Value(("${FRONT_PAGE_SIGNUP}"))
	String redirectPage;

	public ResponseEntity<ApiResponse<String>> kakaoLogin(String authorizationCode) {
		String kakaoToken = kakaoClient.getAccessToken(authorizationCode); // 인가코드로부터 카카오토큰 발급
		KakaoUserInfo kakaoUserInfo = kakaoClient.getUserInfo((kakaoToken));
		String userKakaoEmail = kakaoUserInfo.kakaoAccount().email(); // 와 사용자 카카오 이메일이야

		Optional<Member> existMember = memberService.findMemberByEmail(userKakaoEmail);
		if (existMember.isPresent()) {
			String serviceToken =
					jwtUtil.generateToken(memberService.getMemberId(userKakaoEmail)); // 서비스 토큰 줘야징
			return ResponseEntity.ok((ApiResponse.with(HttpStatus.OK, "로그인 성공", serviceToken)));
		} else {
			return ResponseEntity.status(HttpStatus.FOUND)
					.location(
							URI.create(redirectPage + URLEncoder.encode(userKakaoEmail, StandardCharsets.UTF_8)))
					.build();
		}
	}

	public ResponseEntity<ApiResponse<String>> register(
			String email, String deliveryName, String phoneNumber) {
		Member member = new Member(email, deliveryName, phoneNumber);
		memberService.registerMember(member);
		Long memberId = memberService.getMemberId(email);
		String serviceToken = jwtUtil.generateToken(memberId);
		return ResponseEntity.ok((ApiResponse.with(HttpStatus.OK, "회원가입 및 로그인 성공", serviceToken)));
	}
}
