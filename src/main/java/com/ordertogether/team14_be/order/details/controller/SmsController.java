package com.ordertogether.team14_be.order.details.controller;

import com.ordertogether.team14_be.member.persistence.MemberRepository;
import com.ordertogether.team14_be.member.persistence.entity.Member;
import com.ordertogether.team14_be.member.presentation.LoginMember;
import com.ordertogether.team14_be.order.details.service.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SmsController {

	private final SmsService smsService;
	private final MemberRepository memberRepository;

	@GetMapping("/spot/sms/{spotId}")
	public ResponseEntity<Void> getOrdersInfo(@LoginMember Member member, @PathVariable Long spotId) {
		smsService.sendLinkToParticipant(member, spotId);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/orders/sms/{spotId}/{participantId}")
	public ResponseEntity<Void> getOrdersInfo(
			@LoginMember Member creator, @PathVariable Long spotId, @PathVariable Long participantId) {
		smsService.remindToParticipant(participantId, spotId);
		return ResponseEntity.ok().build();
	}
}
