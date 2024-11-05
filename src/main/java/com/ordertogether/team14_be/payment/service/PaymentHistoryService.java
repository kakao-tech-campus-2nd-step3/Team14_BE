package com.ordertogether.team14_be.payment.service;

import com.ordertogether.team14_be.payment.domain.PaymentStatus;
import com.ordertogether.team14_be.payment.persistence.repository.PaymentOrderRepository;
import com.ordertogether.team14_be.payment.web.dto.PaymentHistory;
import com.ordertogether.team14_be.payment.web.response.PaymentHistoryResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentHistoryService {

	private final PaymentOrderRepository paymentOrderRepository;

	public PaymentHistoryResponse getChargeHistory(Long memberId, PaymentStatus paymentStatus) {
		List<PaymentHistory> histories =
				paymentOrderRepository.getChargeHistory(memberId, paymentStatus);
		return PaymentHistoryResponse.builder().histories(histories).build();
	}
}
