package com.ordertogether.team14_be.payment.web.toss.config;

import java.util.Base64;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@ConfigurationProperties(prefix = "pg.toss")
@RequiredArgsConstructor
public class TossPaymentsClientConfig {

	private final String secretKey;
	private final String url;

	@Bean
	public RestClient tossRestClient(RestClient.Builder builder) {
		return builder
				.defaultHeader(HttpHeaders.AUTHORIZATION, getBasicAuthorization())
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.baseUrl(url)
				.build();
	}

	private String getBasicAuthorization() {
		return "Basic " + Base64.getEncoder().encodeToString((secretKey + ":").getBytes());
	}
}
