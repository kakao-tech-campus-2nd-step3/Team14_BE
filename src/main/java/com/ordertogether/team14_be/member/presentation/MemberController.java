package com.ordertogether.team14_be.member.presentation;

import com.ordertogether.team14_be.common.web.response.ApiResponse;
import com.ordertogether.team14_be.member.application.dto.MemberInfoRequest;
import com.ordertogether.team14_be.member.application.dto.MemberInfoResponse;
import com.ordertogether.team14_be.member.application.service.MemberService;
import com.ordertogether.team14_be.member.persistence.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/members")
public class MemberController {

	private final MemberService memberService;

	@GetMapping("/{memberId}")
	public ResponseEntity<ApiResponse<MemberInfoResponse>> getMemberInfo(
			@PathVariable Long memberId) {
		MemberInfoResponse data = memberService.findMemberInfo(memberId);
		return ResponseEntity.ok(ApiResponse.with(HttpStatus.OK, "회원 정보를 가져옵니다.", data));
	}

	@PutMapping("/{memberId}")
	public ResponseEntity<ApiResponse<MemberInfoResponse>> modifyMemberInfo(
			@PathVariable Long memberId, @RequestBody MemberInfoRequest memberInfoRequest) {
		MemberInfoResponse data = memberService.modifyMember(memberId, memberInfoRequest);
		return ResponseEntity.ok(ApiResponse.with(HttpStatus.OK, "회원 정보가 수정되었습니다.", data));
	}

	// 전화번호랑 닉네임만

	// 토큰을 가지고 세부 정보를 입력하고 회원가입 후 넘겨주기
	@PostMapping("/detail")
	public void signUpMember(
			@LoginMember Member member, @RequestBody MemberInfoRequest memberInfoRequest) {}

	// 토큰이랑 사용자 추가 정보

	@DeleteMapping("/{memberId}") // 회원 탈퇴
	public ResponseEntity<ApiResponse> deleteMember(@PathVariable Long memberId) {
		memberService.deleteMember(memberId);
		return ResponseEntity.ok(ApiResponse.with(HttpStatus.OK, "회원 정보가 수정되었습니다.", ""));
	}
}
