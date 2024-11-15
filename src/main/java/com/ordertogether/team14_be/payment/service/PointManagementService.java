package com.ordertogether.team14_be.payment.service;

import com.ordertogether.team14_be.member.application.service.MemberService;
import com.ordertogether.team14_be.payment.domain.PaymentEvent;
import com.ordertogether.team14_be.payment.persistence.repository.PaymentEventRepository;
import com.ordertogether.team14_be.payment.web.response.PointResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PointManagementService {

	private final PaymentEventRepository paymentEventRepository;
	private final MemberService memberService;

	@Transactional
	public PointResponse increasePoint(String orderId) {
		PaymentEvent paymentEvent =
				paymentEventRepository
						.findByOrderId(orderId)
						.orElseThrow(
								() ->
										new IllegalArgumentException(
												"orderId : %s 에 해당하는 PaymentEvent 가 없습니다.".formatted(orderId)));

		Integer remainingPoint =
				memberService
						.findMember(paymentEvent.getBuyerId())
						.increasePoint(paymentEvent.totalAmount().intValue());

		return PointResponse.builder().remainingPoint(remainingPoint).build();
	}

	@Transactional
	public PointResponse decreasePoint(Long memberId, Integer point) {
		Integer remainingPoint = memberService.findMember(memberId).decreasePoint(point);
		return PointResponse.builder().remainingPoint(remainingPoint).build();
	}
}
