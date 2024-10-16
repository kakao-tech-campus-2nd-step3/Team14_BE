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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/members")
public class MemberController {

	private final MemberService memberService;

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
	public ResponseEntity<ApiResponse> deleteMember(@LoginMember Member member) {
		memberService.deleteMember(member.getId());
		return ResponseEntity.ok(ApiResponse.with(HttpStatus.OK, "회원 정보가 삭제되었습니다.", ""));
	}
}
