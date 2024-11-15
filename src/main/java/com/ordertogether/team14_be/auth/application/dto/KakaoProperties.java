package com.ordertogether.team14_be.auth.application.dto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.LinkedMultiValueMap;

@ConfigurationProperties(prefix = "kakao")
@Slf4j
public record KakaoProperties(
		String clientId, String redirectUrl, String authTokenUrl, String userApiUrl) {
	public LinkedMultiValueMap<String, String> createBody(String code) {
		LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();

		log.info("clientId: {}", clientId());
		log.info("redirectUrl: {}", redirectUrl());
		log.info("code: {}", code);

		body.add("grant_type", "authorization_code");
		body.add("client_id", clientId());
		body.add("redirect_uri", redirectUrl());
		body.add("code", code);

		return body;
	}
}
