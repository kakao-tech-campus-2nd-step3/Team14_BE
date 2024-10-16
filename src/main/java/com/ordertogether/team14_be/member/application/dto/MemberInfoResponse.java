package com.ordertogether.team14_be.member.application.dto;

import lombok.Builder;

@Builder
public record MemberInfoResponse(String deliveryName, String phoneNumber, int point) {}
