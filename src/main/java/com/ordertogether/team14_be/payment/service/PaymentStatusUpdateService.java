package com.ordertogether.team14_be.payment.service;

import com.ordertogether.team14_be.payment.persistence.repository.PaymentEventRepository;
import com.ordertogether.team14_be.payment.service.command.PaymentStatusUpdateCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentStatusUpdateService {

	private final PaymentEventRepository paymentEventRepository;

	@Transactional
	public Integer updatePaymentStatusToExecuting(String orderId, String paymentKey) {
		return paymentEventRepository.updatePaymentStatusToExecuting(orderId, paymentKey);
	}

	@Transactional
	public Integer updatePaymentStatus(PaymentStatusUpdateCommand command) {
		return paymentEventRepository.updatePaymentStatus(
				command.paymentKey(), command.orderId(), command.paymentStatus());
	}
}
