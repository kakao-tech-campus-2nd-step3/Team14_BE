package com.ordertogether.team14_be.auth.presentation;

import com.ordertogether.team14_be.auth.application.dto.KakaoAccessToken;
import com.ordertogether.team14_be.auth.application.dto.KakaoProperties;
import com.ordertogether.team14_be.auth.application.dto.KakaoUserInfo;
import java.net.URI;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class KakaoClient {

	private final RestClient restClient;
	private final KakaoProperties kakaoProperties;

	public String getAccessToken(String authorizationCode) {
		LinkedMultiValueMap<String, String> body = kakaoProperties.createBody(authorizationCode);

		var response =
				restClient
						.post()
						.uri(URI.create(kakaoProperties.authTokenUrl()))
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.body(body)
						.retrieve()
						.toEntity(KakaoAccessToken.class);

		return Objects.requireNonNull(response.getBody()).accessToken();
	}

	public KakaoUserInfo getUserInfo(String accessToken) {
		return restClient
				.get()
				.uri(kakaoProperties.userApiUrl())
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.toEntity(KakaoUserInfo.class)
				.getBody();
	}
}
