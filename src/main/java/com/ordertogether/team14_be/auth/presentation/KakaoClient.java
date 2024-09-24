package com.ordertogether.team14_be.auth.presentation;

import com.ordertogether.team14_be.auth.application.dto.KakaoAccessToken;
import com.ordertogether.team14_be.auth.application.dto.KakaoProperties;
import com.ordertogether.team14_be.auth.application.dto.KakaoUserInfo;
import java.net.URI;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class KakaoClient {

	@Autowired private final RestClient restClient;
	private final KakaoProperties kakaoProperties;

	@Value("${kakao.auth.token.url}")
	private String kakaoTokenUrl;

	@Value("${kakao.user.api.url}")
	private String kakaoUserApiUrl;

	public String getAccessToken(String authorizationCode) {
		LinkedMultiValueMap<String, String> body = kakaoProperties.createBody(authorizationCode);

		var response =
				restClient
						.post()
						.uri(URI.create(kakaoTokenUrl))
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.body(body)
						.retrieve()
						.toEntity(KakaoAccessToken.class);

		return Objects.requireNonNull(response.getBody()).accessToken();
	}

	public KakaoUserInfo getUserInfo(String accessToken) {
		return restClient
				.get()
				.uri(kakaoUserApiUrl)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.toEntity(KakaoUserInfo.class)
				.getBody();
	}
}
