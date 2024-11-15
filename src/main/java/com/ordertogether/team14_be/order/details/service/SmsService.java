package com.ordertogether.team14_be.order.details.service;

import com.ordertogether.team14_be.member.persistence.MemberRepository;
import com.ordertogether.team14_be.member.persistence.entity.Member;
import com.ordertogether.team14_be.order.details.util.SmsUtil;
import com.ordertogether.team14_be.spot.entity.Spot;
import com.ordertogether.team14_be.spot.repository.SimpleSpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmsService {
	private final SmsUtil smsUtil;
	private final SimpleSpotRepository simpleSpotRepository;
	private final MemberRepository memberRepository;

	public ResponseEntity<?> sendLinkToParticipant(Member member, Long spotId) {
		// 수신번호 형태에 맞춰 "-"을 ""로 변환
		String phoneNum = member.getPhoneNumber().replaceAll("-", "");

		Spot spot =
				simpleSpotRepository
						.findById(spotId)
						.orElseThrow(() -> new IllegalArgumentException("스팟 정보가 없습니다."));

		String link = spot.getTogetherOrderLink();
		smsUtil.sendOne(phoneNum, link);

		return ResponseEntity.ok().build();
	}

	public ResponseEntity<?> remindToParticipant(Long spotId, Long participantId) {
		// 수신번호 형태에 맞춰 "-"을 ""로 변환
		Member participant =
				memberRepository
						.findById(participantId)
						.orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));

		String phoneNum = participant.getPhoneNumber().replaceAll("-", "");

		Spot spot =
				simpleSpotRepository
						.findById(spotId)
						.orElseThrow(() -> new IllegalArgumentException("스팟 정보가 없습니다."));

		String link = spot.getTogetherOrderLink();
		smsUtil.remindOrder(phoneNum, link);

		return ResponseEntity.ok().build();
	}
}
