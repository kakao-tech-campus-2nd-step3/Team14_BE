package com.ordertogether.team14_be.payment.persistence.repository;

import com.ordertogether.team14_be.payment.domain.PaymentEvent;
import com.ordertogether.team14_be.payment.domain.PaymentStatus;
import java.util.Optional;

public interface PaymentEventRepository {

	PaymentEvent save(PaymentEvent paymentEvent);

	Optional<PaymentEvent> findByOrderId(String orderId);

	Integer updatePaymentStatus(String paymentKey, String orderId, PaymentStatus paymentStatus);

	/**
	 * 주문 상태를 '실행 중'으로 변경합니다.<br>
	 * - PSP 에서 전달받은 결제 식별자 paymentKey 로 paymentKey 필드를 초기화합니다.<br>
	 * - 주문 상태를 '실행 중'으로 변경합니다.
	 *
	 * @param orderId 주문 번호
	 * @param paymentKey PSP 에서 전달 받은 결제 식별자
	 * @return 변경된 행의 수
	 */
	Integer updatePaymentStatusToExecuting(String orderId, String paymentKey);
}
