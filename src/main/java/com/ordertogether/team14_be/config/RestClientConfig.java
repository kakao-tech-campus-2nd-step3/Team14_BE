package com.ordertogether.team14_be.config;

import java.time.Duration;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

	@Bean
	public RestClient restClient() {
		ClientHttpRequestFactorySettings settings =
				ClientHttpRequestFactorySettings.DEFAULTS
						.withConnectTimeout(Duration.ofSeconds(5))
						.withReadTimeout(Duration.ofSeconds(2));

		ClientHttpRequestFactory requestFactory = ClientHttpRequestFactories.get(settings);

		return RestClient.builder().requestFactory(requestFactory).build();
	}
}
