package com.ordertogether.team14_be.auth.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KakaoUserInfo(
        @JsonProperty("kakao_account")
        KakaoAccount kakaoAccount
) {
}
