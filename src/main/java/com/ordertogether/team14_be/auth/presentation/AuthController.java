package com.ordertogether.team14_be.auth.presentation;

import com.ordertogether.team14_be.auth.application.dto.TokenDto;
import com.ordertogether.team14_be.auth.application.service.AuthService;
import com.ordertogether.team14_be.auth.application.service.KakaoAuthService;
import com.ordertogether.team14_be.common.web.response.ApiResponse;
import com.ordertogether.team14_be.member.application.dto.MemberInfoRequest;
import com.ordertogether.team14_be.member.application.service.MemberService;
import com.ordertogether.team14_be.member.persistence.entity.Member;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {

	private final AuthService authService;
	private final KakaoAuthService kakaoAuthService;
	private final String redirectPage;
	private final MemberService memberService;

	public AuthController(
			AuthService authService,
			KakaoAuthService kakaoAuthService,
			MemberService memberService,
			@Value("${FRONT_PAGE_SIGNUP}") String redirectPage) {
		this.authService = authService;
		this.kakaoAuthService = kakaoAuthService;
		this.memberService = memberService;
		this.redirectPage = redirectPage;
	}

	@GetMapping("/login")
	public ResponseEntity<ApiResponse<TokenDto>> getToken(
			@RequestHeader("Authorization") String authorizationHeader,
			HttpServletResponse httpServletResponse) {
		String authorizationCode = authorizationHeader.replace("Bearer ", "");
		log.info("인가 코드: {}", authorizationCode);
		String userKakaoEmail = kakaoAuthService.getKakaoUserEmail(authorizationCode);
		log.info("카카오 이메일: {}", userKakaoEmail);
		Optional<Member> existMember = memberService.findMemberByEmail(userKakaoEmail);
		log.info("회원: {}", existMember);
		if (existMember.isPresent()) {
			String serviceToken = authService.getServiceToken(userKakaoEmail);
			TokenDto tokenDto = new TokenDto(serviceToken);

			ResponseCookie cookie =
					ResponseCookie.from("serviceToken", serviceToken)
							.httpOnly(true)
							.secure(true)
							.path("/")
							.sameSite("Strict")
							.build();
			log.info("쿠키: {}", cookie);

			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.SET_COOKIE, cookie.toString());

			return ResponseEntity.ok()
					.headers(headers)
					.body(ApiResponse.with(HttpStatus.OK, "로그인 성공", tokenDto));
		} else {
			String redirectUrl = redirectPage + userKakaoEmail;
			log.info("리다이렉트: {}", redirectUrl);
			try {
				httpServletResponse.sendRedirect(redirectUrl);
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
			return ResponseEntity.status(HttpStatus.FOUND)
					.body(ApiResponse.with(HttpStatus.FOUND, "리다이렉트", null));
		}
	}

	@PostMapping("/signup")
	public ResponseEntity<ApiResponse<TokenDto>> signUpMember(
			@RequestParam String email, @Valid @RequestBody MemberInfoRequest memberInfoRequest) {
		String serviceToken =
				kakaoAuthService.register(
						email, memberInfoRequest.deliveryName(), memberInfoRequest.phoneNumber());
		log.info("서비스 토큰: {}", serviceToken);
		TokenDto tokenDto = new TokenDto(serviceToken);
		ResponseCookie cookie =
				ResponseCookie.from("serviceToken", serviceToken)
						.httpOnly(true)
						.secure(true)
						.path("/")
						.sameSite("Strict")
						.build();

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.SET_COOKIE, cookie.toString());

		return ResponseEntity.ok()
				.headers(headers)
				.body(ApiResponse.with(HttpStatus.OK, "회원가입 성공", tokenDto));
	}

	@PostMapping("/logout")
	public ResponseEntity<ApiResponse<String>> logout(HttpServletResponse response) {
		ResponseCookie deleteCookie =
				ResponseCookie.from("serviceToken", "")
						.maxAge(0)
						.httpOnly(true)
						.secure(true)
						.path("/")
						.sameSite("Strict")
						.build();

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.SET_COOKIE, deleteCookie.toString());

		return ResponseEntity.ok()
				.headers(headers)
				.body(ApiResponse.with(HttpStatus.OK, "로그아웃 성공", ""));
	}
}
