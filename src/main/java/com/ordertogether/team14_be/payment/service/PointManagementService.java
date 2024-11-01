package com.ordertogether.team14_be.payment.service;

import com.ordertogether.team14_be.member.application.service.MemberService;
import com.ordertogether.team14_be.payment.domain.PaymentEvent;
import com.ordertogether.team14_be.payment.persistence.repository.PaymentEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PointManagementService {

	private final PaymentEventRepository paymentEventRepository;
	private final MemberService memberService;

	@Transactional
	public Integer increasePoint(String orderId) {
		PaymentEvent paymentEvent =
				paymentEventRepository
						.findByOrderId(orderId)
						.orElseThrow(
								() ->
										new IllegalArgumentException(
												"orderId : %s 에 해당하는 PaymentEvent 가 없습니다.".formatted(orderId)));

		return memberService
				.findMember(paymentEvent.getBuyerId())
				.increasePoint(paymentEvent.totalAmount().intValue());
	}
}
