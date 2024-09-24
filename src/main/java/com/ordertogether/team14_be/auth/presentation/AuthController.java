package com.ordertogether.team14_be.auth.presentation;

import com.ordertogether.team14_be.auth.application.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	private final AuthService authService;

	@GetMapping("/login")
	public String getToken(@RequestHeader String authorizationCode) {
		return authService.kakaoLogin(authorizationCode);
	}
}
