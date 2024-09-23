package com.ordertogether.team14_be.auth.application.service;

import com.ordertogether.team14_be.auth.JwtUtil;
import com.ordertogether.team14_be.auth.application.dto.KakaoAccessToken;
import com.ordertogether.team14_be.auth.application.dto.KakaoUserInfo;
import com.ordertogether.team14_be.auth.application.dto.TokenResponseDto;
import com.ordertogether.team14_be.auth.presentation.KakaoClient;
import com.ordertogether.team14_be.memebr.application.service.MemberService;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  private final KakaoClient kakaoClient;
  private final MemberService memberService;

  public AuthService(KakaoClient kakaoClient, MemberService memberService) {
    this.kakaoClient = kakaoClient;
    this.memberService = memberService;
  }

  public String kakaoLogin(String authorizationCode) {
    String kakaoToken = kakaoClient.getAccessToken(authorizationCode); // 인가코드로부터 카카오토큰 발급
    KakaoUserInfo kakaoUserInfo = kakaoClient.getUserInfo((kakaoToken));
    String userKakaoEmail = kakaoUserInfo.kakaoAccount().email();
    memberService.findOrCreateMember(userKakaoEmail);
    String serviceToken = JwtUtil.generateToken(userKakaoEmail);

    return serviceToken;
  }

}
