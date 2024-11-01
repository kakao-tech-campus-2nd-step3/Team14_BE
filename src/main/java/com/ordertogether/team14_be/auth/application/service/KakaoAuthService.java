package com.ordertogether.team14_be.auth.application.service;

import com.ordertogether.team14_be.auth.application.dto.KakaoUserInfo;
import com.ordertogether.team14_be.auth.presentation.KakaoClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KakaoAuthService {
	private final KakaoClient kakaoClient;

	public String getKakaoUserEmail(String authorizationCode) {
		String kakaoToken = kakaoClient.getAccessToken(authorizationCode);
		KakaoUserInfo kakaoUserInfo = kakaoClient.getUserInfo((kakaoToken));
		String userKakaoEmail = kakaoUserInfo.kakaoAccount().email();
		return userKakaoEmail;
	}
}
