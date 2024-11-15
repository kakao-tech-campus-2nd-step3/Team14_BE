package com.ordertogether.team14_be.member.presentation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import com.ordertogether.team14_be.auth.persistence.JwtUtil;
import com.ordertogether.team14_be.common.web.response.ApiResponse;
import com.ordertogether.team14_be.member.application.dto.MemberInfoRequest;
import com.ordertogether.team14_be.member.application.dto.MemberInfoResponse;
import com.ordertogether.team14_be.member.application.service.MemberService;
import com.ordertogether.team14_be.member.persistence.entity.Member;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

class MemberControllerTest {

	@Mock private MemberService memberService;

	@Mock private JwtUtil jwtUtil;

	@InjectMocks private MemberController memberController;

	@Mock private Member member;
	private MemberInfoResponse memberInfoResponse;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		when(member.getId()).thenReturn(1L);
		memberInfoResponse = new MemberInfoResponse("deliveryName", "010-1234-5678", 0);
	}

	@Test
	void getMemberInfo_Success() {
		when(memberService.findMemberInfo(member.getId())).thenReturn(memberInfoResponse);

		ResponseEntity<ApiResponse<MemberInfoResponse>> response =
				memberController.getMemberInfo(member);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals("회원 정보를 가져옵니다.", response.getBody().getMessage());
		assertEquals(memberInfoResponse, response.getBody().getData());
		verify(memberService, times(1)).findMemberInfo(member.getId());
	}

	@Test
	void modifyMemberInfo_Success() {
		MemberInfoRequest memberInfoRequest = new MemberInfoRequest("deliveryName", "010-5678-1234");

		MemberInfoResponse updatedResponse = new MemberInfoResponse("deliveryName", "010-5678-1234", 0);

		when(memberService.modifyMember(anyLong(), eq("deliveryName"), eq("010-5678-1234")))
				.thenReturn(updatedResponse);

		ResponseEntity<ApiResponse<MemberInfoResponse>> response =
				memberController.modifyMemberInfo(member, memberInfoRequest);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals("회원 정보가 수정되었습니다.", response.getBody().getMessage());
		assertEquals(updatedResponse, response.getBody().getData());
		verify(memberService, times(1))
				.modifyMember(anyLong(), eq("deliveryName"), eq("010-5678-1234"));
	}

	@Test
	void deleteMember_Success() {
		String authorizationHeader = "Bearer testToken";
		HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);

		Claims claims = mock(Claims.class);
		when(claims.getSubject()).thenReturn("1");
		when(jwtUtil.decodeJwt("testToken")).thenReturn(claims);

		doNothing().when(memberService).deleteMember(anyLong());

		ResponseEntity<ApiResponse> response =
				memberController.deleteMember(authorizationHeader, httpServletResponse);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals("회원탈퇴 성공", response.getBody().getMessage());
		verify(jwtUtil, times(1)).decodeJwt("testToken");
		verify(memberService, times(1)).deleteMember(anyLong());
		assertEquals("", response.getBody().getData());
		assertEquals(1, response.getHeaders().get(HttpHeaders.SET_COOKIE).size());
	}
}
