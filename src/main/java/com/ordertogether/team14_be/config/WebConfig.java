package com.ordertogether.team14_be.config;

import com.ordertogether.team14_be.member.application.LoginMemberArgumentResolver;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	private final LoginMemberArgumentResolver loginMemberArgumentResolver;

	public WebConfig(LoginMemberArgumentResolver loginMemberArgumentResolver) {
		this.loginMemberArgumentResolver = loginMemberArgumentResolver;
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(loginMemberArgumentResolver);
	}
}
