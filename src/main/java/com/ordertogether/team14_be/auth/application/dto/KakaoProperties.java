package com.ordertogether.team14_be.auth.application.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.LinkedMultiValueMap;

@ConfigurationProperties(prefix = "kakao")
public record KakaoProperties(
		String clientId, String redirectUrl, String authTokenUrl, String userApiUrl) {
	public LinkedMultiValueMap<String, String> createBody(String code) {
		LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();

		body.add("grant_type", "authorization_code");
		body.add("client_id", clientId());
		body.add("redirect_uri", redirectUrl());
		body.add("code", code);

		return body;
	}
}
