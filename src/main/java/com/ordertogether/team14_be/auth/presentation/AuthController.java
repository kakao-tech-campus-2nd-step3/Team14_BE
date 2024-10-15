package com.ordertogether.team14_be.auth.presentation;

import com.ordertogether.team14_be.auth.application.service.AuthService;
import com.ordertogether.team14_be.common.web.response.ApiResponse;
import com.ordertogether.team14_be.member.application.dto.MemberInfoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	private final AuthService authService;

	@GetMapping("/login")
	public ResponseEntity<ApiResponse<String>> getToken(@RequestHeader String authorizationCode) {
		return authService.kakaoLogin(authorizationCode);
	}

	@PostMapping("/signup")
	public ResponseEntity<ApiResponse<String>> signUpMember(
			@RequestParam String email, @RequestBody MemberInfoRequest memberInfoRequest) {
		return authService.register(
				email, memberInfoRequest.deliveryName(), memberInfoRequest.phoneNumber());
	}
}
