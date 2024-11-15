package com.ordertogether.team14_be.member.application.dto;

import jakarta.validation.constraints.NotBlank;

public record MemberInfoRequest(
		@NotBlank(message = "필수 입력 사항입니다.") String deliveryName,
		@NotBlank(message = "필수 입력 사항입니다.") String phoneNumber) {}
