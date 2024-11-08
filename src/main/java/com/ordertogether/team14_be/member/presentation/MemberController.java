package com.ordertogether.team14_be.member.presentation;

import com.ordertogether.team14_be.auth.persistence.JwtUtil;
import com.ordertogether.team14_be.common.web.response.ApiResponse;
import com.ordertogether.team14_be.member.application.dto.MemberInfoRequest;
import com.ordertogether.team14_be.member.application.dto.MemberInfoResponse;
import com.ordertogether.team14_be.member.application.service.MemberService;
import com.ordertogether.team14_be.member.persistence.entity.Member;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/members")
public class MemberController {
	private final MemberService memberService;
	private final JwtUtil jwtUtil;

	@GetMapping
	public ResponseEntity<ApiResponse<MemberInfoResponse>> getMemberInfo(@LoginMember Member member) {
		MemberInfoResponse data = memberService.findMemberInfo(member.getId());
		return ResponseEntity.ok(ApiResponse.with(HttpStatus.OK, "회원 정보를 가져옵니다.", data));
	}

	@PutMapping
	public ResponseEntity<ApiResponse<MemberInfoResponse>> modifyMemberInfo(
			@LoginMember Member member, @RequestBody MemberInfoRequest memberInfoRequest) {
		MemberInfoResponse data =
				memberService.modifyMember(
						member.getId(), memberInfoRequest.deliveryName(), memberInfoRequest.phoneNumber());
		return ResponseEntity.ok(ApiResponse.with(HttpStatus.OK, "회원 정보가 수정되었습니다.", data));
	}

	@DeleteMapping
	public ResponseEntity<ApiResponse> deleteMember(
			@RequestHeader("Authorization") String authorizationHeader,
			HttpServletResponse httpServletResponse) {
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

		String serviceToken = authorizationHeader.replace("Bearer ", "");
		String stringMemberId = jwtUtil.decodeJwt(serviceToken).getSubject();
		long memberId = Integer.parseInt(stringMemberId);
		memberService.deleteMember(memberId);
		return ResponseEntity.ok()
				.headers(headers)
				.body(ApiResponse.with(HttpStatus.OK, "회원탈퇴 성공", ""));
	}
}
