package com.ordertogether.team14_be.payment.service;

import com.ordertogether.team14_be.payment.persistence.repository.PaymentOrderRepository;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
/** 결제 유효성 검사 */
public class PaymentValidationService {

	private final PaymentOrderRepository paymentOrderRepository;

	/**
	 * 요청된 결제 금액과 실제 결제 금액이 일치하는 지 검증합니다.
	 *
	 * @param orderId 주문 번호
	 * @param requestedAmount 요청한 결제 금액
	 * @return 결제 금액이 일치하면 true, 그렇지 않으면 {@link IllegalArgumentException} 발생
	 */
	public boolean validate(String orderId, BigDecimal requestedAmount) {
		BigDecimal expectedAmount = paymentOrderRepository.getPaymentTotalAmount(orderId);

		if (isAmountMismatch(requestedAmount, expectedAmount)) {
			throw new IllegalArgumentException(
					"주문 번호: %s 의 결제 요청 금액 %s 원은 예상 결제 금액 %s 원과 다릅니다."
							.formatted(orderId, requestedAmount, expectedAmount));
		}

		return true;
	}

	private boolean isAmountMismatch(BigDecimal requestedAmount, BigDecimal expectedAmount) {
		return requestedAmount.compareTo(expectedAmount) != 0;
	}
}
